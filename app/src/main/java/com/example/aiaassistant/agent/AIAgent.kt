package com.example.aiaassistant.agent

import com.example.aiaassistant.agent.model.Blueprint
import com.example.aiaassistant.agent.model.LLMRequest
import com.example.aiaassistant.agent.model.LLMResponse

class AIAgent(
    private val apiService: LLMApiService?,
    private val blueprintParser: BlueprintParser,
    private val allowedTemplates: List<String> = listOf(
        "radio_form_v1",
        "mind_map_v1",
        "doc_card_v1",
        "timeline_v1",
        "slider_v1",
        "canvas_v1"
    )
) {
    suspend fun generateBlueprint(prompt: String, context: String? = null): BlueprintResult {
        return try {
            if (apiService != null) {
                val request = LLMRequest(
                    prompt = prompt,
                    context = context,
                    allowedTemplates = allowedTemplates
                )
                val response = apiService.generateBlueprint(request)
                if (response.isSuccessful && response.body() != null) {
                    handleResponse(response.body()!!)
                } else {
                    BlueprintResult.Error("API request failed")
                }
            } else {
                generateMockBlueprint(prompt)
            }
        } catch (e: Exception) {
            generateMockBlueprint(prompt)
        }
    }

    private fun handleResponse(response: LLMResponse): BlueprintResult {
        return if (response.success && response.blueprint != null) {
            if (blueprintParser.validate(response.blueprint)) {
                BlueprintResult.Success(response.blueprint)
            } else {
                BlueprintResult.Error("Invalid blueprint structure")
            }
        } else {
            BlueprintResult.Error(response.error ?: "Unknown error")
        }
    }

    private fun generateMockBlueprint(prompt: String): BlueprintResult {
        val mockBlueprint = when {
            prompt.contains("问题") || prompt.contains("测验") || prompt.contains("问答") -> {
                createRadioFormBlueprint()
            }
            prompt.contains("图谱") || prompt.contains("关系") -> {
                createMindMapBlueprint()
            }
            else -> {
                createDocCardBlueprint(prompt)
            }
        }
        return BlueprintResult.Success(mockBlueprint)
    }

    private fun createRadioFormBlueprint(): Blueprint {
        return blueprintParser.parse("""
            {
                "template": "radio_form_v1",
                "data_bind": {
                    "content": {
                        "title": "知识测验",
                        "body": "请选择正确的答案",
                        "items": [
                            {"id": "1", "text": "选项 A", "isCorrect": false},
                            {"id": "2", "text": "选项 B", "isCorrect": true},
                            {"id": "3", "text": "选项 C", "isCorrect": false},
                            {"id": "4", "text": "选项 D", "isCorrect": false}
                        ]
                    }
                },
                "navigation": {
                    "next_action": "submit",
                    "next_template": "doc_card_v1"
                }
            }
        """.trimIndent()) ?: createDefaultBlueprint()
    }

    private fun createMindMapBlueprint(): Blueprint {
        return blueprintParser.parse("""
            {
                "template": "mind_map_v1",
                "data_bind": {
                    "content": {
                        "title": "知识图谱",
                        "items": [
                            {"id": "1", "text": "核心概念"},
                            {"id": "2", "text": "子主题 A"},
                            {"id": "3", "text": "子主题 B"},
                            {"id": "4", "text": "子主题 C"}
                        ]
                    }
                },
                "navigation": {
                    "next_action": "explore",
                    "params": {"zoom": "1.0"}
                }
            }
        """.trimIndent()) ?: createDefaultBlueprint()
    }

    private fun createDocCardBlueprint(prompt: String): Blueprint {
        return blueprintParser.parse("""
            {
                "template": "doc_card_v1",
                "data_bind": {
                    "content": {
                        "title": "智能回复",
                        "body": "根据您的问题：'$prompt'，这是AI助手为您提供的回答内容。\n\n主要要点：\n• 这是一个示例文档卡片\n• 支持Markdown格式显示\n• 可滚动查看详细内容\n• 支持高亮批注功能"
                    }
                },
                "navigation": {
                    "next_action": "none"
                }
            }
        """.trimIndent()) ?: createDefaultBlueprint()
    }

    private fun createDefaultBlueprint(): Blueprint {
        return blueprintParser.parse("""
            {
                "template": "doc_card_v1",
                "data_bind": {
                    "content": {
                        "title": "默认回复",
                        "body": "这是AI助手的默认回复内容。"
                    }
                },
                "navigation": {
                    "next_action": "none"
                }
            }
        """.trimIndent())!!
    }
}

sealed class BlueprintResult {
    data class Success(val blueprint: Blueprint) : BlueprintResult()
    data class Error(val message: String) : BlueprintResult()
}