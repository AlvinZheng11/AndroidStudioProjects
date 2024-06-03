package com.example.viewanimationexample

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    private var zoomCounter = 0
    private var start = false
    private var imageView : ImageView? = null
    private var fadeOutMyViewComplete = false
    private var fadeOutCompleteViewComplete = false
    private var repeatCount = 5
    private var origtouchedRectangle = Rect(0,0,0,0)
    private var moveCompleted1 = false
    private var moveCompleted2 = false


    companion object
    {
        private var instance : MainActivity? = null
        public fun getInstance() : MainActivity
        {
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        setContentView(R.layout.activity_main)

        //Set the imageView once
        imageView = ImageView(this)
        //Add event handlers
        val fadeOut = findViewById<Button>(R.id.fade_out)
        val fadeIn = findViewById<Button>(R.id.fade_in)
        val dissolve = findViewById<Button>(R.id.dissolve)
        val zoom = findViewById<Button>(R.id.zoom)
        val move = findViewById<Button>(R.id.move)
        val rotate = findViewById<Button>(R.id.rotate)
        var tiger = findViewById<ImageView>(R.id.tiger)
        tiger.scaleX = -1.0f   //Flips horizontally
        tiger.scaleY = -1.0f //Flips vertically

        val handler = Handler()

        fadeOut.setOnClickListener(handler)
        fadeIn.setOnClickListener(handler)
        dissolve.setOnClickListener(handler)
        zoom.setOnClickListener(handler)
        move.setOnClickListener(handler)
        rotate.setOnClickListener(handler)
        //Set the imageView once
        imageView = ImageView(this)


    }

    inner class Handler : View.OnClickListener
    {
        override fun onClick(v: View?)
        {
            val text = (v as Button).text
            var zoomCounter = 0
            if (text == "Fade Out")
            {
                val myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
                val animHandler = FadeOutHandler()
                myFadeOut.setAnimationListener(animHandler)

                val untouched = findViewById<ImageView>(R.id.untouched)
                untouched.startAnimation(myFadeOut)

            }
            else if (text == "Fade In")
            {
                val myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
                val animHandler1 = FadeInHandler()
                myFadeIn.setAnimationListener(animHandler1)
                val untouched = findViewById<ImageView>(R.id.untouched)
                untouched.startAnimation(myFadeIn)
            }
            else if (text == "Dissolve")
            {
                //Fade out the target
                val myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
                val animHandler1 = DissolveHandler()
                myFadeOut.setAnimationListener(animHandler1)
                val untouched = findViewById<ImageView>(R.id.untouched)
                untouched.startAnimation(myFadeOut)

            }
            else if (text == "Zoom")
            {
                zoomCounter++
                if(zoomCounter >= repeatCount)
                    return
                else
                {
                    val myZoom = AnimationUtils.loadAnimation(MainActivity.getInstance(),R.anim.zoom_in)
                    val zoomHandler = ZoomHandler()
                    myZoom.setAnimationListener(zoomHandler)
                    val touched = MainActivity.getInstance().findViewById<ImageView>(R.id.touched)

                    touched.startAnimation(myZoom)
                }
                //Restart the animation for 5 times
                println("End animation")



            }
            else if (text == "Rotate")
            {
                val myRotate = AnimationUtils.loadAnimation(MainActivity.getInstance(),R.anim.rotate)
                val rotateHandler = RotateHandler()
                myRotate.setAnimationListener(rotateHandler)
                val touched = MainActivity.getInstance().findViewById<ImageView>(R.id.touched)
                touched.startAnimation(myRotate)

            }
            else if (text == "Move")
            {
                val untouched = findViewById<ImageView>(R.id.untouched)
                val touched = findViewById<ImageView>(R.id.touched)

                origtouchedRectangle = Rect(touched.left,touched.top,touched.right, touched.bottom)

                //Compute the deltas
                val deltaX =  (untouched.left - touched.left).toFloat()
                val deltaY =  (untouched.top - touched.top).toFloat()

                val moveAnim = TranslateAnimation(0f, deltaX, 0f, deltaY)
                moveAnim.duration = 500
                moveAnim.fillAfter = true

                val handler = MoveHandler()
                moveAnim.setAnimationListener(handler)
                touched.startAnimation(moveAnim)
            }

        }
    }

    /*********************FadeOutHandler**********************************/
    inner class FadeOutHandler : Animation.AnimationListener
    {
        override fun onAnimationRepeat(animation: Animation?)
        {
            println("repeat")
        }
        override fun onAnimationEnd(animation: Animation?)
        {
            println("Fade out finshied")
            val myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in)
            val animHandler1 = FadeInHandler()
            myFadeIn.setAnimationListener(animHandler1)
            val untouched = findViewById<ImageView>(R.id.untouched)
            untouched.startAnimation(myFadeIn)

        }
        override fun onAnimationStart(animation: Animation?)
        {
            println("Fade out start")
        }
    }
    /****************************************************************/
    /**************************FadeInHandler**********************************************/
    inner class FadeInHandler : Animation.AnimationListener
    {
        override fun onAnimationRepeat(animation: Animation?)
        {
            println("repeat")
        }
        override fun onAnimationEnd(animation: Animation?)
        {
            println("Fade in finshied")

        }
        override fun onAnimationStart(animation: Animation?)
        {

            println("Fade in start")
        }
    }
    /**********************************************************************************/
    /***********************************Dissolve Handler**********************************/
    inner class DissolveHandler : Animation.AnimationListener
    {
        override fun onAnimationRepeat(animation: Animation?)
        {
            println("repeat")
        }
        override fun onAnimationEnd(animation: Animation?)
        {
            println("dissolve finished")

            val untouched = findViewById<ImageView>(R.id.untouched)
            untouched.alpha = 0.0f
            imageView?.alpha = 0.0F
        }
        override fun onAnimationStart(animation: Animation?)
        {
            println("dissolve start")
            imageView?.setImageResource(R.drawable.tiger1)
            val untouched = findViewById<ImageView>(R.id.untouched)
            imageView?.layoutParams = untouched.layoutParams
            //Be sure to add only once!
            if (!start)
            {
                val cl = findViewById<ConstraintLayout>(R.id.cl)
                cl.addView(imageView)
                start = true
            }
            val myFadeIn = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_in )
            val animHandler2 = FadeInHandler()
            myFadeIn.setAnimationListener(animHandler2)
            imageView?.startAnimation(myFadeIn)
        }
    }
    /*************************************************************************************************/
    /***********************************Zoom in Handler**********************************/

    inner class ZoomHandler : Animation.AnimationListener
    {
        override fun onAnimationRepeat(animation: Animation?)
        {
            println("repeat")
        }
        override fun onAnimationEnd(animation: Animation?)
        {

            println("End animation")
        }
        override fun onAnimationStart(animation: Animation?)
        {
            println("zoom start")
        }
    }
    /*************************************************************************************************/
    /******************Rotate Handler*****************************/
    inner class RotateHandler : Animation.AnimationListener
    {
        override fun onAnimationRepeat(animation: Animation?)
        {
            println("repeat")
        }
        override fun onAnimationEnd(animation: Animation?)
        {
            println("rotate finished")

        }
        override fun onAnimationStart(animation: Animation?)
        {
            println("rotate start")
        }
    }
    /**************************************************************/
    /*********************Move Handler**********************************/
    inner class MoveHandler : Animation.AnimationListener
    {
        override fun onAnimationRepeat(animation: Animation?)
        {
            println("repeat")
        }
        override fun onAnimationEnd(animation: Animation?)
        {
            val untouched = findViewById<ImageView>(R.id.untouched)
            val touched = findViewById<ImageView>(R.id.touched)
            val tiger = findViewById<ImageView>(R.id.tiger)

            if (!moveCompleted1)
            {
                //Reset the untouched coordinates
                touched.left = untouched.left
                touched.top = untouched.top
                touched.bottom = untouched.bottom
                touched.right = untouched.right
                moveCompleted1 = true
                //Compute the deltas
                val deltaX = (tiger.left - untouched.left).toFloat()
                val deltaY = (tiger.top - untouched.top).toFloat()
                val moveAnim = TranslateAnimation(0f, deltaX, 0f, deltaY)
                moveAnim.duration = 500
                moveAnim.fillAfter = true
                val handler = MoveHandler()
                moveAnim.setAnimationListener(handler)
                touched.startAnimation(moveAnim)
            }
            else if (!moveCompleted2)
            {
                touched.left = tiger.left
                touched.top = tiger.top
                touched.right = tiger.right
                touched.bottom = tiger.bottom
                moveCompleted2 = true

                //Compute the deltas back to the original
                val deltaX = (origtouchedRectangle.left - tiger.left).toFloat()
                val deltaY = (origtouchedRectangle.top - tiger.top).toFloat()
                val moveAnim = TranslateAnimation(0f, deltaX, 0f, deltaY)
                moveAnim.duration = 500
                moveAnim.fillAfter = true
                val handler = MoveHandler()
                moveAnim.setAnimationListener(handler)
                touched.startAnimation(moveAnim)
            }
            println("move finished")
        }
        override fun onAnimationStart(animation: Animation?)
        {
            println("move start")
        }
    }
    /****************************************************************/

}