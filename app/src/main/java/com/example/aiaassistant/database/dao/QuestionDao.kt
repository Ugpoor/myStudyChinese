package com.example.aiaassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.aiaassistant.database.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions ORDER BY createdAt DESC")
    fun getAllQuestions(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE id = :id")
    suspend fun getQuestionById(id: Long): QuestionEntity?

    @Query("SELECT * FROM questions WHERE type = :type ORDER BY createdAt DESC")
    fun getQuestionsByType(type: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE category = :category ORDER BY createdAt DESC")
    fun getQuestionsByCategory(category: String): Flow<List<QuestionEntity>>

    @Insert
    suspend fun insertQuestion(question: QuestionEntity): Long

    @Update
    suspend fun updateQuestion(question: QuestionEntity)

    @Query("DELETE FROM questions WHERE id = :id")
    suspend fun deleteQuestion(id: Long)
}