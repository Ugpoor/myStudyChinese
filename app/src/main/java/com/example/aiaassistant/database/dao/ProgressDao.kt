package com.example.aiaassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.aiaassistant.database.entity.ProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM progress WHERE questionId = :questionId")
    fun getProgressByQuestionId(questionId: Long): Flow<ProgressEntity?>

    @Query("SELECT * FROM progress WHERE isCompleted = :completed ORDER BY timestamp DESC")
    fun getProgressByCompletion(completed: Boolean): Flow<List<ProgressEntity>>

    @Insert
    suspend fun insertProgress(progress: ProgressEntity): Long

    @Update
    suspend fun updateProgress(progress: ProgressEntity)

    @Query("DELETE FROM progress WHERE id = :id")
    suspend fun deleteProgress(id: Long)
}