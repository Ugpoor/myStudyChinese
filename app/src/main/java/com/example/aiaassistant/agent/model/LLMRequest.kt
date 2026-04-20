package com.example.aiaassistant.agent.model

import com.google.gson.annotations.SerializedName

data class LLMRequest(
    @SerializedName("prompt") val prompt: String,
    @SerializedName("context") val context: String?,
    @SerializedName("allowed_templates") val allowedTemplates: List<String>
)