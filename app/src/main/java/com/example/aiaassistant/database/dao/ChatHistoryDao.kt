package com.example.aiaassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.aiaassistant.database.entity.ChatHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatHistoryDao {
    @Query("SELECT * FROM chat_history ORDER BY timestamp ASC")
    fun getAllChatHistory(): Flow<List<ChatHistoryEntity>>

    @Query("SELECT * FROM chat_history WHERE id = :id")
    suspend fun getChatHistoryById(id: Long): ChatHistoryEntity?

    @Insert
    suspend fun insertChatHistory(chatHistory: ChatHistoryEntity): Long

    @Query("DELETE FROM chat_history WHERE id = :id")
    suspend fun deleteChatHistory(id: Long)

    @Query("DELETE FROM chat_history")
    suspend fun clearChatHistory()
}