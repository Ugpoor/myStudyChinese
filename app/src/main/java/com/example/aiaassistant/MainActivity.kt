package com.example.aiaassistant

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.aiaassistant.agent.BlueprintResult
import com.example.aiaassistant.database.entity.ChatHistoryEntity
import com.example.aiaassistant.processing.TemplateEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var contentLayout: LinearLayout
    private lateinit var inputText: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        contentLayout = findViewById(R.id.content_layout)
        inputText = findViewById(R.id.input_text)
        sendButton = findViewById(R.id.send_button)

        sendButton.setOnClickListener {
            val prompt = inputText.text.toString().trim()
            if (prompt.isNotEmpty()) {
                processPrompt(prompt)
            }
        }

        inputText.setOnEditorActionListener { _, _, _ ->
            val prompt = inputText.text.toString().trim()
            if (prompt.isNotEmpty()) {
                processPrompt(prompt)
            }
            true
        }

        loadWelcomeMessage()
        loadChatHistory()
    }

    private fun loadWelcomeMessage() {
        val welcomeView = createMessageView("欢迎使用AI助手！请输入您的问题，我将为您提供帮助。", isUser = false)
        contentLayout.addView(welcomeView)
    }

    private fun loadChatHistory() {
        CoroutineScope(Dispatchers.IO).launch {
            val chatHistory = (application as App).chatHistoryRepository.getAllChatHistory()
            chatHistory.collect { historyList ->
                withContext(Dispatchers.Main) {
                    historyList.forEach { item ->
                        val messageView = createMessageView(item.content, item.role == "user")
                        contentLayout.addView(messageView)
                    }
                }
            }
        }
    }

    private fun processPrompt(prompt: String) {
        val userMessageView = createMessageView(prompt, isUser = true)
        contentLayout.addView(userMessageView)
        inputText.text.clear()

        val loadingView = createLoadingView()
        contentLayout.addView(loadingView)

        CoroutineScope(Dispatchers.IO).launch {
            saveChatMessage(prompt, "user", null)

            val result = (application as App).aiAgent.generateBlueprint(prompt)

            withContext(Dispatchers.Main) {
                contentLayout.removeView(loadingView)

                when (result) {
                    is BlueprintResult.Success -> {
                        val blueprint = result.blueprint
                        saveChatMessage(blueprint.dataBind.content?.title ?: "回复", "assistant", blueprint.toString())

                        val renderedView = TemplateEngine.render(this@MainActivity, blueprint)
                        if (renderedView != null) {
                            contentLayout.addView(renderedView)
                        } else {
                            val errorView = createMessageView("无法渲染模板: ${blueprint.template}", isUser = false)
                            contentLayout.addView(errorView)
                        }
                    }
                    is BlueprintResult.Error -> {
                        val errorView = createMessageView("错误: ${result.message}", isUser = false)
                        contentLayout.addView(errorView)
                    }
                }
            }
        }
    }

    private fun saveChatMessage(content: String, role: String, blueprint: String?) {
        val chatHistory = ChatHistoryEntity(
            role = role,
            content = content,
            blueprint = blueprint
        )
        (application as App).chatHistoryRepository.insertChatHistory(chatHistory)
    }

    private fun createMessageView(message: String, isUser: Boolean): View {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 16, 0, 0)
            }
        }

        if (!isUser) {
            val avatar = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(48, 48).apply {
                    marginEnd = 12
                }
                background = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_launcher)
            }
            layout.addView(avatar)
        }

        val messageView = TextView(this).apply {
            text = message
            textSize = 16f
            setPadding(16, 12, 16, 12)
            background = if (isUser) {
                ContextCompat.getDrawable(this@MainActivity, R.drawable.user_message_bg)
            } else {
                ContextCompat.getDrawable(this@MainActivity, R.drawable.assistant_message_bg)
            }
            setTextColor(if (isUser) ContextCompat.getColor(this@MainActivity, R.color.white) else ContextCompat.getColor(this@MainActivity, R.color.black))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                weight = 0f
                width = LinearLayout.LayoutParams.WRAP_CONTENT
                maxWidth = resources.displayMetrics.widthPixels - 120
            }
        }
        layout.addView(messageView)

        if (isUser) {
            layout.gravity = android.view.Gravity.END
        }

        return layout
    }

    private fun createLoadingView(): View {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 16, 0, 0)
            }
        }

        val avatar = View(this).apply {
            layoutParams = LinearLayout.LayoutParams(48, 48).apply {
                marginEnd = 12
            }
            background = ContextCompat.getDrawable(this, R.drawable.ic_launcher)
        }
        layout.addView(avatar)

        val progressBar = ProgressBar(this).apply {
            layoutParams = LinearLayout.LayoutParams(48, 48)
            isIndeterminate = true
        }
        layout.addView(progressBar)

        return layout
    }
}