package com.example.aiaassistant.processing.templates

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.aiaassistant.agent.model.Blueprint
import com.example.aiaassistant.processing.TemplateRenderer
import com.example.aiaassistant.R

class RadioFormRenderer : TemplateRenderer {
    override fun getTemplateName(): String = "radio_form_v1"

    override fun render(context: Context, blueprint: Blueprint): View {
        val data = blueprint.dataBind.content
        val linearLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        data?.title?.let { title ->
            val titleView = TextView(context).apply {
                text = title
                textSize = 20f
                setTextColor(ContextCompat.getColor(context, R.color.black))
                gravity = Gravity.CENTER_HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 16
                }
            }
            linearLayout.addView(titleView)
        }

        data?.body?.let { body ->
            val bodyView = TextView(context).apply {
                text = body
                textSize = 16f
                setTextColor(ContextCompat.getColor(context, R.color.gray_600))
                gravity = Gravity.CENTER_HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 24
                }
            }
            linearLayout.addView(bodyView)
        }

        val radioGroup = RadioGroup(context).apply {
            orientation = RadioGroup.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        data?.items?.forEachIndexed { index, item ->
            val cardView = CardView(context).apply {
                radius = 12f
                setCardBackgroundColor(Color.WHITE)
                cardElevation = 4f
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = if (index == 0) 0 else 12
                }
            }

            val radioButton = RadioButton(context).apply {
                id = View.generateViewId()
                text = item.text
                textSize = 16f
                setTextColor(ContextCompat.getColor(context, R.color.black))
                buttonTintList = ContextCompat.getColorStateList(context, R.color.purple_500)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setPadding(16, 16, 16, 16)
                }
            }

            cardView.addView(radioButton)
            radioGroup.addView(cardView)
        }

        linearLayout.addView(radioGroup)

        blueprint.navigation.nextAction?.let { action ->
            val button = Button(context).apply {
                text = if (action == "submit") "提交答案" else "下一步"
                textSize = 16f
                setBackgroundColor(ContextCompat.getColor(context, R.color.purple_500))
                setTextColor(Color.WHITE)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 24
                    height = 56
                }
                setOnClickListener {
                    val selectedId = radioGroup.checkedRadioButtonId
                    if (selectedId != -1) {
                        Toast.makeText(context, "已选择选项", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "请选择一个选项", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            linearLayout.addView(button)
        }

        return linearLayout
    }
}