package com.example.aiaassistant.agent.model

import com.google.gson.annotations.SerializedName

data class Blueprint(
    @SerializedName("template") val template: String,
    @SerializedName("data_bind") val dataBind: DataBind,
    @SerializedName("navigation") val navigation: Navigation
)

data class DataBind(
    @SerializedName("query") val query: QueryConfig?,
    @SerializedName("content") val content: ContentData?
)

data class QueryConfig(
    @SerializedName("table") val table: String,
    @SerializedName("conditions") val conditions: List<Condition>?,
    @SerializedName("limit") val limit: Int = 10
)

data class Condition(
    @SerializedName("field") val field: String,
    @SerializedName("operator") val operator: String,
    @SerializedName("value") val value: String
)

data class ContentData(
    @SerializedName("title") val title: String?,
    @SerializedName("body") val body: String?,
    @SerializedName("items") val items: List<ItemData>?
)

data class ItemData(
    @SerializedName("id") val id: String,
    @SerializedName("text") val text: String,
    @SerializedName("isCorrect") val isCorrect: Boolean = false
)

data class Navigation(
    @SerializedName("next_action") val nextAction: String?,
    @SerializedName("next_template") val nextTemplate: String?,
    @SerializedName("params") val params: Map<String, String>?
)