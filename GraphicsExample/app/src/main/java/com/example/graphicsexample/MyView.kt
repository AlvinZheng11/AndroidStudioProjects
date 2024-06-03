package com.example.graphicsexample

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView

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

    private var x1 : Float = 100.0f
    private var y1 : Float = 100.0f
    private var ball : ImageView? = null
    private var targets = ArrayList<Drawable>() //Targets
    private var start : Boolean = true          ///Display the ball properly
    private var rectCoords : Rect = Rect(0,0,0,0)
    private var circleCoords : RectF = RectF(0.0f,0.0f,0.0f,0.0f)
    private var ballCoords : Rect = Rect(0,0,0,0)
    private var ball_touched = false
    private var startBallPoint : Point = Point(0,0)
    private var offsetBall : Point = Point(0,0)
    private var firstTouch = false
    private var prevTime  = 0L


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)  //One per widget

    init {
        rectCoords.set(80, 100,250, 250)
        circleCoords.set(300.0f,300.0f,450.0f,450.0f)
        instance = this
    }

    public fun setRectCoords(ux : Int, uy : Int, lx : Int, ly : Int)
    {
        this.rectCoords.set(ux,uy,lx,ly)
    }
    public fun setCircleCoords(ux : Float, uy : Float, lx : Float, ly : Float)
    {
        this.circleCoords.set(ux,uy,lx,ly)
    }
    public fun setballCoords(ux : Int, uy : Int, lx : Int, ly : Int)
    {
        this.ballCoords.set(ux,uy,lx,ly)
    }
    public fun getRectCoords() : Rect
    {
        return rectCoords
    }
    public fun getCircleCoords() : RectF
    {
        return circleCoords
    }
    public fun getBallCoords() : Rect
    {
        return ballCoords
    }


    public fun setCoords(x1 : Float, y1 : Float)
    {
        this.x1 = x1
        this.y1 = y1
    }


    public fun getX1() : Float
    {
        return x1
    }
    public fun getY1() : Float
    {
        return y1
    }

    override fun onDraw(canvas: Canvas)
    {
        super.onDraw(canvas)
        paint.color = Color.BLACK
        //Draw a black rectangle

        canvas.drawRect(rectCoords,paint)  //Move the black Rectangle
        paint.color = Color.BLUE
        canvas.drawOval(circleCoords,paint) //Move the oval


        ball = MainActivity.getInstance().findViewById<ImageView>(R.id.ball)

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


//Turn the rectangle yellow when cannonball intersects using the following: ul of CB
        if ( (ball?.left!! > rectCoords.left) && (ball?.top!! > rectCoords.top) &&
            (ball?.left!! < rectCoords.right) && (ball?.top!! < rectCoords.bottom))
        {
            paint.color = Color.YELLOW
            canvas.drawRect(rectCoords,paint)
        }


        if ( (ball?.left!! > circleCoords.left) && (ball?.top!! > circleCoords.top) &&
            (ball?.left!! < circleCoords.right) && (ball?.top!! < circleCoords.bottom))
        {
            paint.color = Color.CYAN
            canvas.drawOval(circleCoords,paint) //Move the oval

            val timer = MainActivity.getInstance().getTimer()
            timer.cancel()

            //Fire a new timer
            MainActivity.getInstance().fire()
        }
        else if ( (ball?.right!! > circleCoords.right) && (ball?.top!! > circleCoords.top) &&
            (ball?.right!! < circleCoords.right) && (ball?.top!! < circleCoords.bottom))
        {
            paint.color = Color.CYAN
            canvas.drawOval(circleCoords,paint) //Move the oval

            val timer = MainActivity.getInstance().getTimer()
            timer.cancel()

            //Fire a new timer
            MainActivity.getInstance().fire()
        }
        else
        {
            paint.color = Color.YELLOW
            canvas.drawOval(circleCoords,paint) //Move the oval
            println(circleCoords)
            println(ballCoords)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
    {
        super.onSizeChanged(w, h, oldw, oldh)
        val width = this.width
        val height = this.height
        //Run only once per class
        for (i in 0..10)
        {
            val x = (Math.random() * width).toInt()
            val y = (Math.random() * height).toInt()
            val imageView = ImageView(MainActivity.getInstance())
            imageView.setImageResource(R.drawable.untouched)
            val drawable = imageView.drawable
            drawable.setBounds(x,y,x+100,y+100) //Sets the dimensions
            targets.add(drawable)  //stores away the image
        }
        ball = MainActivity.getInstance().findViewById<ImageView>(R.id.ball)

        var handler = Handler1()

        var remove =  MainActivity.getInstance().findViewById<Button>(R.id.remove)
        //remove.setOnClickListener(handler)

    }

    inner  class Handler1 : View.OnClickListener
    {
        private var counter : Int = 0
        private var total : Int = targets.size
        override fun onClick(v: View?)
        {
            if (counter < total)
            {
                //Get the drawable
                targets.removeAt(0)

                MyView.getInstance().invalidate()
                counter++
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        val x = (event.x)
        val y = (event.y)
        val currentTime = System.currentTimeMillis()
        var index = event.actionIndex
        println("Finger Index#: $index")


        if (event.action == MotionEvent.ACTION_DOWN )
        {
            println("touched down initially - $x, $y")
//            if( (firstTouch) && (currentTime - prevTime <= 300L) )
//            {
//                //do stuff here for double tap
//                println("** DOUBLE TAP** second tap ");
//                firstTouch = false;
//                return false
//            }
//            else
//            {
//                firstTouch = true;
//                prevTime = System.currentTimeMillis()
//                println("** SINGLE  TAP**  First Tap time  $prevTime");
//                return false;
//            }

            //Verify if touch inside the ball
            if ( (x > ball?.left!!) && (x < ball?.right!!) &&
                (y > ball?.top!!) && (y < ball?.bottom!!))
            {
                ballCoords = Rect(ball?.left!!,ball?.top!!,ball?.right!!,ball?.bottom!!)
                println("ballTouched")
                ball_touched = true

                startBallPoint.set(x.toInt(),y.toInt())
                offsetBall.set((x - ballCoords.left).toInt(), (y - ballCoords.top).toInt())

            }



        }
        else if (event.action ==  MotionEvent.ACTION_MOVE)
        {
            println("moving finger 1 $x, $y")
            for (i in 0..<event.pointerCount)
            {
                var xp = event.getX(event.getPointerId(i))
                var yp = event.getY(event.getPointerId(i))
                println("Moving  finger $i $xp $yp ")
            }

            println("moving $x, $y")

            if (ball_touched)
            {
                ballCoords.left = x.toInt() - offsetBall.x
                ballCoords.top = y.toInt() - offsetBall.y
                ballCoords.right = (x + ball?.width!! - offsetBall.x).toInt()
                ballCoords.bottom = (y + ball?.height!! - offsetBall.y).toInt()
                this.invalidate()
            }

        }
        else if (event.actionMasked == MotionEvent.ACTION_POINTER_DOWN)
        {
            var xp  = event.getX(event.getPointerId(index))
            var yp = event.getY(event.getPointerId(index))
            println("Pointer down $xp $yp ")
        }

        else if (event.action == MotionEvent.ACTION_UP)
        {
            println("touched up - $x, $y")
            ball_touched = false

        }

        return true  //Use up this event prevents the bubbling to a parent of this view
    }



}