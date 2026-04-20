package com.example.aiaassistant

import android.app.Application
import androidx.room.Room
import com.example.aiaassistant.agent.AIAgent
import com.example.aiaassistant.agent.BlueprintParser
import com.example.aiaassistant.database.AppDatabase
import com.example.aiaassistant.database.repository.ChatHistoryRepository
import com.example.aiaassistant.database.repository.QuestionRepository
import com.google.gson.Gson

class App : Application() {
    lateinit var database: AppDatabase
    lateinit var aiAgent: AIAgent
    lateinit var questionRepository: QuestionRepository
    lateinit var chatHistoryRepository: ChatHistoryRepository

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "ai_assistant.db"
        ).build()

        val gson = Gson()
        val blueprintParser = BlueprintParser(gson)
        aiAgent = AIAgent(null, blueprintParser)

        questionRepository = QuestionRepository(database.questionDao())
        chatHistoryRepository = ChatHistoryRepository(database.chatHistoryDao())
    }
}