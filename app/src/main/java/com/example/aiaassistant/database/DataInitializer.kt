package com.example.aiaassistant.database

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.aiaassistant.database.entity.AnswerEntity
import com.example.aiaassistant.database.entity.QuestionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataInitializer : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
    }

    fun populateInitialData(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val appDatabase = (context.applicationContext as com.example.aiaassistant.App).database

            val questions = listOf(
                QuestionEntity(
                    content = "人工智能的主要应用领域有哪些？",
                    type = "radio",
                    category = "AI基础",
                    difficulty = 1
                ),
                QuestionEntity(
                    content = "机器学习中监督学习和无监督学习的区别是什么？",
                    type = "radio",
                    category = "机器学习",
                    difficulty = 2
                ),
                QuestionEntity(
                    content = "深度学习的基本单元是什么？",
                    type = "radio",
                    category = "深度学习",
                    difficulty = 1
                )
            )

            questions.forEach { question ->
                val questionId = appDatabase.questionDao().insertQuestion(question)

                when (question.content) {
                    "人工智能的主要应用领域有哪些？" -> {
                        val answers = listOf(
                            AnswerEntity(questionId = questionId, content = "自然语言处理", isCorrect = false, orderIndex = 0),
                            AnswerEntity(questionId = questionId, content = "计算机视觉", isCorrect = false, orderIndex = 1),
                            AnswerEntity(questionId = questionId, content = "语音识别", isCorrect = false, orderIndex = 2),
                            AnswerEntity(questionId = questionId, content = "以上都是", isCorrect = true, orderIndex = 3)
                        )
                        appDatabase.answerDao().insertAnswers(answers)
                    }
                    "机器学习中监督学习和无监督学习的区别是什么？" -> {
                        val answers = listOf(
                            AnswerEntity(questionId = questionId, content = "监督学习需要标注数据，无监督学习不需要", isCorrect = true, orderIndex = 0),
                            AnswerEntity(questionId = questionId, content = "无监督学习需要标注数据，监督学习不需要", isCorrect = false, orderIndex = 1),
                            AnswerEntity(questionId = questionId, content = "两者都需要标注数据", isCorrect = false, orderIndex = 2),
                            AnswerEntity(questionId = questionId, content = "两者都不需要标注数据", isCorrect = false, orderIndex = 3)
                        )
                        appDatabase.answerDao().insertAnswers(answers)
                    }
                    "深度学习的基本单元是什么？" -> {
                        val answers = listOf(
                            AnswerEntity(questionId = questionId, content = "决策树", isCorrect = false, orderIndex = 0),
                            AnswerEntity(questionId = questionId, content = "神经元/感知器", isCorrect = true, orderIndex = 1),
                            AnswerEntity(questionId = questionId, content = "支持向量机", isCorrect = false, orderIndex = 2),
                            AnswerEntity(questionId = questionId, content = "随机森林", isCorrect = false, orderIndex = 3)
                        )
                        appDatabase.answerDao().insertAnswers(answers)
                    }
                }
            }
        }
    }
}