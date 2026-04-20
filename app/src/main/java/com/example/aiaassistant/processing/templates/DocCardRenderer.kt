package com.example.aiaassistant.processing.templates

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.aiaassistant.agent.model.Blueprint
import com.example.aiaassistant.processing.TemplateRenderer
import com.example.aiaassistant.R

class DocCardRenderer : TemplateRenderer {
    override fun getTemplateName(): String = "doc_card_v1"

    override fun render(context: Context, blueprint: Blueprint): View {
        val data = blueprint.dataBind.content
        val cardView = CardView(context).apply {
            radius = 16f
            setCardBackgroundColor(Color.WHITE)
            cardElevation = 8f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, 16, 16, 16)
            }
        }

        val linearLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
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
                setTextColor(ContextCompat.getColor(context, R.color.gray_800))
                lineSpacingExtra = 8f
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }
            linearLayout.addView(bodyView)
        }

        cardView.addView(linearLayout)
        return cardView
    }
}