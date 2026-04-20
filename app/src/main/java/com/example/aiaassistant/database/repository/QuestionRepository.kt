package com.example.aiaassistant.database.repository

import com.example.aiaassistant.database.dao.QuestionDao
import com.example.aiaassistant.database.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

class QuestionRepository(private val questionDao: QuestionDao) {
    fun getAllQuestions(): Flow<List<QuestionEntity>> = questionDao.getAllQuestions()
    fun getQuestionsByType(type: String): Flow<List<QuestionEntity>> = questionDao.getQuestionsByType(type)
    fun getQuestionsByCategory(category: String): Flow<List<QuestionEntity>> = questionDao.getQuestionsByCategory(category)
    suspend fun getQuestionById(id: Long): QuestionEntity? = questionDao.getQuestionById(id)
    suspend fun insertQuestion(question: QuestionEntity): Long = questionDao.insertQuestion(question)
    suspend fun updateQuestion(question: QuestionEntity) = questionDao.updateQuestion(question)
    suspend fun deleteQuestion(id: Long) = questionDao.deleteQuestion(id)
}