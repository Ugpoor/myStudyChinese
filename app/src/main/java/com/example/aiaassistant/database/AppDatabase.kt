package com.example.aiaassistant.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aiaassistant.database.dao.*
import com.example.aiaassistant.database.entity.*

@Database(
    entities = [
        QuestionEntity::class,
        AnswerEntity::class,
        ProgressEntity::class,
        ChatHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun answerDao(): AnswerDao
    abstract fun progressDao(): ProgressDao
    abstract fun chatHistoryDao(): ChatHistoryDao
}