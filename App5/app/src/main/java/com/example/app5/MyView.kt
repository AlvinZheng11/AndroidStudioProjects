package com.example.app5

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import java.lang.Math.cos
import java.lang.Math.sin

class MyView(context: Context, attrs: AttributeSet) : View(context, attrs)
{
    companion object
    {
        private var instance : MyView? = null
        public fun getInstance() : MyView
        {
            return instance!!
        }
    }

    private var ball : ImageView? = null
    var targets = ArrayList<Drawable>() //Targets
    private var rectCoords : Rect = Rect(0,0,0,0)
    private var ballCoords : Rect = Rect(0,0,0,0)
    private var angle : Float = 0.0F
    private var barrelLength : Float = 200F
    private var barrelX : Float = (width*.12).toFloat()
    private var barrelY : Float = (.9*height).toFloat()
    private var start : Boolean = true          ///Display the ball properly
    var targetsGone = 0


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)  //One per widget

    init {
        instance = this
    }
    public fun setballCoords(ux: Int, uy: Int, lx: Int, ly: Int)
    {
        this.ballCoords.set(ux,uy,lx,ly)
    }
    public fun getBallCoords() : Rect
    {
        return ballCoords
    }

    override fun onDraw(canvas: Canvas)
    {
        super.onDraw(canvas)
        barrelX  = (width*.13).toFloat()
        barrelY  = (.85*height).toFloat()
        barrelLength = (.06*width).toFloat()
        ball = MainActivity.getInstance().findViewById<ImageView>(R.id.ball)

        //Draw bottom bar
        paint.color = Color.parseColor("#7F7F00")
        rectCoords.set(0, height,width, (.9*height).toInt())
        canvas.drawRect(rectCoords,paint)

        //Draw barrel
        paint.color = Color.BLACK
        paint.strokeWidth = 30F
        canvas.drawLine(barrelX, barrelY, this.findX(), this.findY(), paint)

        //Draw the targets normally –
        for (i in 0..<targets.size)
        {
            val drawable = targets[i]
            drawable.draw(canvas)
        }

        if (start)
        {
            start = false
        }
        else
        {
            ball?.setLeftTopRightBottom(ballCoords.left, ballCoords.top, ballCoords.right,
                ballCoords.bottom)
        }
        var x = ((.5 * width).toInt())
        var y = ((.15 * height).toInt())
        val scale = (.045 * width).toInt()
        var found = false
        var count = 0
        var rowCount = 0
        val score = MainActivity.getInstance().findViewById<TextView>(R.id.scores)

        for (i in 0..<targets.size) {
            if (found) {
                targetsGone +=1
                targets.removeAt(count)
                val text = "score: $targetsGone"
                score.text = text
                found = false
                count = 0
            }
            if (rowCount == 7) {
                x = ((.5 * width).toInt())
                y += 100
                rowCount = 0
            }
            if ( (ball?.left!! > x) && (ball?.top!! > y) && (ball?.right!! < (x+scale)) && (ball?.bottom!! < (y+scale))) {
                found = true
            }
            else {
                count++
                rowCount++
                x += 100
            }
            this.invalidate()
        }
    }




    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
    {
        super.onSizeChanged(w, h, oldw, oldh)
        val width = this.width
        val height = this.height
        var x = ((.5 * width).toInt())
        var y = ((.15 * height).toInt())
        val scale = (.045 * width).toInt()
        //Run only once per class
        for (j in 0..6) {
            for (i in 0..6) {
                val imageView = ImageView(MainActivity.getInstance())
                imageView.setImageResource(R.drawable.untouched)
                val drawable = imageView.drawable
                drawable.setBounds(x, y, x+scale, y+scale) //Sets the dimensions
                targets.add(drawable)  //stores away the image
                x += 100
            }
            x = ((.5 * width).toInt())
            y += 100
        }
        ball = MainActivity.getInstance().findViewById<ImageView>(R.id.ball)

    }

    fun drawBarrel(angle: Double){
        this.angle = Math.toRadians(angle).toFloat()

    }

    fun findX(): Float{
        return ((barrelLength * kotlin.math.cos(angle.toDouble())) + barrelX).toFloat()
    }

    fun findY(): Float{
        return (barrelY - (barrelLength * kotlin.math.sin(angle.toDouble()))).toFloat()
    }

}




