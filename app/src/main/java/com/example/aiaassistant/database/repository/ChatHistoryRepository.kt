package com.example.aiaassistant.database.repository

import com.example.aiaassistant.database.dao.ChatHistoryDao
import com.example.aiaassistant.database.entity.ChatHistoryEntity
import kotlinx.coroutines.flow.Flow

class ChatHistoryRepository(private val chatHistoryDao: ChatHistoryDao) {
    fun getAllChatHistory(): Flow<List<ChatHistoryEntity>> = chatHistoryDao.getAllChatHistory()
    suspend fun getChatHistoryById(id: Long): ChatHistoryEntity? = chatHistoryDao.getChatHistoryById(id)
    suspend fun insertChatHistory(chatHistory: ChatHistoryEntity): Long = chatHistoryDao.insertChatHistory(chatHistory)
    suspend fun deleteChatHistory(id: Long) = chatHistoryDao.deleteChatHistory(id)
    suspend fun clearChatHistory() = chatHistoryDao.clearChatHistory()
}