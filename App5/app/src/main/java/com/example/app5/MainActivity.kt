package com.example.app5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    private var speed = 5
    private var ball_dir = -1
    private var timer = Timer()
    private var angle = 0
    private var ballVelocityX: Float = 0f // Horizontal velocity of the ball
    private var ballVelocityY: Float = 0f // Vertical velocity of the ball
    private var gravity: Float = 9.8f // Gravity constant
    private var lastUpdatedTime = System.nanoTime()

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

        val handler = Handler()

        val fire = findViewById<Button>(R.id.Fire)
        val velocityBar = findViewById<SeekBar>(R.id.velocityBar)
        val angleBar = findViewById<SeekBar>(R.id.angleBar)


        velocityBar.setOnSeekBarChangeListener(handler)
        angleBar.setOnSeekBarChangeListener(handler)
        fire.setOnClickListener(handler)



    }
    private fun initializeBallVelocity(angle: Int, speed: Int) {
        ballVelocityX = speed * kotlin.math.cos(Math.toRadians(angle.toDouble())).toFloat()
        ballVelocityY = speed * kotlin.math.sin(Math.toRadians(angle.toDouble())).toFloat()
    }

    fun update()
    {
        val myView = findViewById<MyView>(R.id.myView)
        val ball = findViewById<ImageView>(R.id.ball)

        //Synchronize with the view getting setup
        if (myView.width > 1)
        {
            ball.left += (ballVelocityX).toInt()
            ball.right += (ballVelocityX).toInt()

            ball.top += (-ballVelocityY).toInt()
            ball.bottom += (-ballVelocityY).toInt()

            ballVelocityY -= gravity/10

            //Check for edges
            if ( (ball.left < 0) || (ball.right > myView.width)) {
//                myView.setballCoords((myView.findX().toInt()), (myView.findY().toInt()) - 25, (myView.findX().toInt()) + 50, (myView.findY().toInt()) + 25)
//                myView.invalidate()
                timer.cancel()
            }
            if ( (ball.top < 0) || (ball.bottom > myView.height)) {
                timer.cancel()
            }
            myView.setballCoords(ball.left, ball.top, ball.right, ball.bottom)
            myView.invalidate()
        }
    }

    fun fire()
    {
        timer = Timer()
        val timerTask = TimerObject()
        timer.schedule(timerTask,0,10)
    }
    fun setSpeed(int: Int){
        speed = int
        initializeBallVelocity(angle, speed)
    }
    fun setAngle(int: Int){
        angle = int
        initializeBallVelocity(angle, speed)
    }
}

class Handler : View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private var count =0
    private var myView: MyView = MainActivity.getInstance().findViewById<MyView>(R.id.myView)

    override fun onClick(v: View?) {
        count+=1
        val shots = MainActivity.getInstance().findViewById<TextView>(R.id.shots)
        val text = "Shots: $count"
        shots.text = text
        myView.setballCoords((myView.findX().toInt()),(myView.findY().toInt())-25,(myView.findX().toInt())+50,(myView.findY().toInt())+25)
        MainActivity.getInstance().fire()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

        if (seekBar != null) {
            if (seekBar.id == R.id.angleBar) {
                val angleValue = MainActivity.getInstance().findViewById<TextView>(R.id.angleValue)
                val text = progress.toString()
                angleValue.text = text
                MainActivity.getInstance().setAngle(progress)
                myView.drawBarrel(progress.toDouble())
                myView.setballCoords((myView.findX().toInt()),(myView.findY().toInt())-25,(myView.findX().toInt())+50,(myView.findY().toInt())+25)
                myView.invalidate()
            }
            else if (seekBar.id == R.id.velocityBar) {
                val velocityValue = MainActivity.getInstance().findViewById<TextView>(R.id.velocityValue)
                val text = progress.toString()
                velocityValue.text = text
                MainActivity.getInstance().setSpeed(progress/2)
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        println("start")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        println("stop")
    }

}



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
        val helper = HelperThread()

        MainActivity.getInstance().runOnUiThread(helper)
    }
}
