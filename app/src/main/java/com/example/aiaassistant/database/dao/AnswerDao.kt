package com.example.aiaassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.aiaassistant.database.entity.AnswerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnswerDao {
    @Query("SELECT * FROM answers WHERE questionId = :questionId ORDER BY orderIndex")
    fun getAnswersByQuestionId(questionId: Long): Flow<List<AnswerEntity>>

    @Query("SELECT * FROM answers WHERE id = :id")
    suspend fun getAnswerById(id: Long): AnswerEntity?

    @Insert
    suspend fun insertAnswer(answer: AnswerEntity): Long

    @Insert
    suspend fun insertAnswers(answers: List<AnswerEntity>)

    @Update
    suspend fun updateAnswer(answer: AnswerEntity)

    @Query("DELETE FROM answers WHERE questionId = :questionId")
    suspend fun deleteAnswersByQuestionId(questionId: Long)
}