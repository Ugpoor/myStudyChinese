package com.example.aiaassistant.agent.model

import com.google.gson.annotations.SerializedName

data class LLMResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("blueprint") val blueprint: Blueprint?,
    @SerializedName("error") val error: String?,
    @SerializedName("content") val content: String?
)