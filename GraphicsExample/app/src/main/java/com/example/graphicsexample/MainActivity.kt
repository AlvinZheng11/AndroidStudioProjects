package com.example.graphicsexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import java.util.Timer
import java.util.TimerTask

class HelperThread : Runnable
{
    override fun run()
    {
        MainActivity.getInstance().update()
    }
}


class TimerObject : TimerTask()
{
    override fun run()
    {
        var helper = HelperThread()

        MainActivity.getInstance().runOnUiThread(helper)
    }
}


class MainActivity : AppCompatActivity()
{
    private var speed = 5;
    private var oval_dir = 1;
    private var ball_dir = -1
    private var timer = Timer()

    public fun getTimer(): Timer
    {
        return timer
    }

    public fun fire()
    {
        timer = Timer()
        var timerTask = TimerObject()
        timer.schedule(timerTask,3000,25)
    }
    companion object
    {
        private var instance : MainActivity? = null
        public fun getInstance() : MainActivity
        {
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        instance = this

        setContentView(R.layout.activity_main)

        var handler = Handler()
        var add1 = findViewById<Button>(R.id.add1)
        var remove = findViewById<Button>(R.id.remove)
        add1.setOnClickListener(handler)
        remove.setOnClickListener(handler)

        var timerTask = TimerObject()
        //timer.schedule(timerTask,0,25)

    }


    public fun update()
    {
        var myView = findViewById<MyView>(R.id.myView)
        var ball = findViewById<ImageView>(R.id.ball)

        //Synchronize with the view getting setup
        if (myView.getWidth() > 1)
        {
            println(myView.getWidth() )
            println(myView.getHeight())
            var circle = myView.getCircleCoords()
            circle.left += (speed*oval_dir)
            circle.right += (speed*oval_dir)
            ball.top += (speed*ball_dir)
            ball.bottom += (speed*ball_dir)
            //Check for edges
            if ( (circle.left < 0) || (circle.right > myView.getWidth()))
                oval_dir *= -1
            if ( (ball.top < 0) || (ball.bottom > myView.getHeight()))
                ball_dir *= -1
            myView.setCircleCoords(circle.left, circle.top, circle.right, circle.bottom)
            myView.setballCoords(ball.left, ball.top, ball.right, ball.bottom)
            myView.invalidate()
        }
    }



    inner class Handler : View.OnClickListener
    {
        override fun onClick(v: View?)
        {
            var text = (v as Button).getText()
            if (text == "+")
                speed++
            else if (text == "-")
                speed--
            if (speed < 0)
                speed = 0
        }
    }

}

