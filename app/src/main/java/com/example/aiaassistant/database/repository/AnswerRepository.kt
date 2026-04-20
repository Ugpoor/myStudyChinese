package com.example.aiaassistant.database.repository

import com.example.aiaassistant.database.dao.AnswerDao
import com.example.aiaassistant.database.entity.AnswerEntity
import kotlinx.coroutines.flow.Flow

class AnswerRepository(private val answerDao: AnswerDao) {
    fun getAnswersByQuestionId(questionId: Long): Flow<List<AnswerEntity>> = answerDao.getAnswersByQuestionId(questionId)
    suspend fun getAnswerById(id: Long): AnswerEntity? = answerDao.getAnswerById(id)
    suspend fun insertAnswer(answer: AnswerEntity): Long = answerDao.insertAnswer(answer)
    suspend fun insertAnswers(answers: List<AnswerEntity>) = answerDao.insertAnswers(answers)
    suspend fun updateAnswer(answer: AnswerEntity) = answerDao.updateAnswer(answer)
    suspend fun deleteAnswersByQuestionId(questionId: Long) = answerDao.deleteAnswersByQuestionId(questionId)
}