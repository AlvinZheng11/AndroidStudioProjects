package com.example.viewanimationexample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
class MyView : View
{
    private var paint = Paint()
    companion object
    {
        private var instance : MyView? = null
        public fun getInstance() : MyView
        {
            return instance!!
        }
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    {
        this.setWillNotDraw(false)
        instance = this
    }
    override fun onDraw(canvas: Canvas)
    {
        super.onDraw(canvas)
        val width = width
        val height = height
        canvas.drawRect((width*0.5).toFloat(), (height*0.5).toFloat(),100.0f, 100.0f,paint)
    }
}
