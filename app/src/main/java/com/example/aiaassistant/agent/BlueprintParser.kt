package com.example.aiaassistant.agent

import com.example.aiaassistant.agent.model.Blueprint
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

class BlueprintParser(private val gson: Gson) {
    fun parse(jsonString: String): Blueprint? {
        return try {
            gson.fromJson(jsonString, Blueprint::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }

    fun toJson(blueprint: Blueprint): String {
        return gson.toJson(blueprint)
    }

    fun validate(blueprint: Blueprint): Boolean {
        if (blueprint.template.isEmpty()) return false
        if (blueprint.dataBind == null) return false
        return true
    }
}