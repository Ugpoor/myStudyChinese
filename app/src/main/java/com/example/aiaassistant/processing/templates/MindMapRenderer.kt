package com.example.aiaassistant.processing.templates

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.aiaassistant.agent.model.Blueprint
import com.example.aiaassistant.processing.TemplateRenderer
import com.example.aiaassistant.R

class MindMapRenderer : TemplateRenderer {
    override fun getTemplateName(): String = "mind_map_v1"

    override fun render(context: Context, blueprint: Blueprint): View {
        val data = blueprint.dataBind.content
        val linearLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        data?.title?.let { title ->
            val titleView = androidx.appcompat.widget.AppCompatTextView(context).apply {
                text = title
                textSize = 20f
                setTextColor(ContextCompat.getColor(context, R.color.black))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(16, 16, 16, 8)
                }
            }
            linearLayout.addView(titleView)
        }

        val items = data?.items ?: emptyList()
        val mindMapView = MindMapCanvas(context, items)
        linearLayout.addView(mindMapView)

        return linearLayout
    }
}

class MindMapCanvas(context: Context, private val items: List<com.example.aiaassistant.agent.model.ItemData>) :
    View(context) {

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    private val centerX get() = width / 2f
    private val centerY get() = height / 2f
    private val radius = 200f
    private val nodeRadius = 40f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (items.isEmpty()) return

        paint.color = ContextCompat.getColor(context, R.color.purple_500)
        paint.style = Paint.Style.FILL
        canvas.drawCircle(centerX, centerY, nodeRadius, paint)

        paint.color = Color.WHITE
        paint.textSize = 14f
        paint.textAlign = Paint.Align.CENTER
        val centerText = items.firstOrNull()?.text ?: "中心"
        canvas.drawText(centerText, centerX, centerY + 5, paint)

        val angleStep = (Math.PI * 2) / (items.size - 1).coerceAtLeast(1)
        items.drop(1).forEachIndexed { index, item ->
            val angle = angleStep * index - Math.PI / 2
            val x = centerX + radius * Math.cos(angle).toFloat()
            val y = centerY + radius * Math.sin(angle).toFloat()

            paint.color = ContextCompat.getColor(context, R.color.teal_200)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 3f
            canvas.drawLine(centerX, centerY, x, y, paint)

            paint.color = ContextCompat.getColor(context, R.color.teal_700)
            paint.style = Paint.Style.FILL
            canvas.drawCircle(x, y, nodeRadius * 0.8f, paint)

            paint.color = Color.WHITE
            paint.textSize = 12f
            val itemText = item.text.take(6)
            canvas.drawText(itemText, x, y + 4, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = 400
        val height = resolveSize(desiredHeight, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, height)
    }

    private var lastTouchX = 0f
    private var lastTouchY = 0f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchX = event.x
                lastTouchY = event.y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - lastTouchX
                val dy = event.y - lastTouchY
                parent.requestDisallowInterceptTouchEvent(true)
                lastTouchX = event.x
                lastTouchY = event.y
            }
        }
        return super.onTouchEvent(event)
    }
}