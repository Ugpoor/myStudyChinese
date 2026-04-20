package com.example.aiaassistant.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val content: String,
    val type: String,
    val category: String,
    val difficulty: Int,
    val createdAt: Long = System.currentTimeMillis()
)