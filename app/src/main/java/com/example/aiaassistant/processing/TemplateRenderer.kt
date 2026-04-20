package com.example.aiaassistant.processing

import android.content.Context
import android.view.View
import com.example.aiaassistant.agent.model.Blueprint

interface TemplateRenderer {
    fun render(context: Context, blueprint: Blueprint): View
    fun getTemplateName(): String
}

class TemplateRegistry {
    private val renderers = mutableMapOf<String, TemplateRenderer>()

    fun register(renderer: TemplateRenderer) {
        renderers[renderer.getTemplateName()] = renderer
    }

    fun getRenderer(templateName: String): TemplateRenderer? {
        return renderers[templateName]
    }

    fun getAvailableTemplates(): List<String> {
        return renderers.keys.toList()
    }
}

object TemplateEngine {
    private val registry = TemplateRegistry()

    init {
        registry.register(RadioFormRenderer())
        registry.register(DocCardRenderer())
        registry.register(MindMapRenderer())
    }

    fun render(context: Context, blueprint: Blueprint): View? {
        val renderer = registry.getRenderer(blueprint.template)
        return renderer?.render(context, blueprint)
    }

    fun getAvailableTemplates(): List<String> {
        return registry.getAvailableTemplates()
    }
}