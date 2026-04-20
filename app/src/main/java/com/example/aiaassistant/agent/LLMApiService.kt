package com.example.aiaassistant.agent

import com.example.aiaassistant.agent.model.LLMRequest
import com.example.aiaassistant.agent.model.LLMResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LLMApiService {
    @POST("/api/v1/generate")
    suspend fun generateBlueprint(@Body request: LLMRequest): Response<LLMResponse>
}