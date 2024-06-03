package com.example.app6

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import java.lang.Thread.sleep

class MyView : View {
    private var lion : ImageView? = null
    private var cobra : ImageView? = null
    private var rabbit : ImageView? = null
    private var lion_touched = false
    private var cobra_touched = false
    private var rabbit_touched = false
    private var lionCoords : Rect = Rect(0,0,0,0)
    private var cobraCoords : Rect = Rect(0,0,0,0)
    private var rabbitCoords : Rect = Rect(0,0,0,0)
    private var playerCoord = Rect(0, 0, 0, 0)
    private var opponentCoord = Rect(0, 0, 0, 0)
    private var startLionPoint : Point = Point(0,0)
    private var offsetLion : Point = Point(0,0)
    private var startCobraPoint : Point = Point(0,0)
    private var offsetCobra : Point = Point(0,0)
    private var startRabbitPoint : Point = Point(0,0)
    private var offsetRabbit : Point = Point(0,0)
    private var startLion = Rect(0,0,0,0)
    private var startCobra = Rect(0,0,0,0)
    private var startRabbit = Rect(0,0,0,0)
    private var backgroundCoord = Rect(0,0,0,0)
    private var start = true
    private var start2 = true
    private var lionImages = arrayOf<Int>(R.drawable.lion0, R.drawable.lion1, R.drawable.lion2, R.drawable.lion3)
    private var opponents = arrayOf<Int>(R.drawable.lion0, R.drawable.cobra, R.drawable.rabbit)
    private var opponent : ImageView? = null
    private var results : TextView? = null

    companion object {
        private var instance : MyView? = null
        public fun getInstance() : MyView {
            return instance!!
        }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.setWillNotDraw(false)
        instance = this
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //sets opponents to invisible
        if (start) {
            start = false
            opponent?.visibility = INVISIBLE
        }
        //sets image coords
        else {
            lion?.x = lionCoords.left.toFloat()
            lion?.y = lionCoords.top.toFloat()
            cobra?.x = cobraCoords.left.toFloat()
            cobra?.y = cobraCoords.top.toFloat()
            rabbit?.x = rabbitCoords.left.toFloat()
            rabbit?.y = rabbitCoords.top.toFloat()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        lion = MainActivity.getInstance().findViewById(R.id.lion)
        cobra = MainActivity.getInstance().findViewById(R.id.cobra)
        rabbit = MainActivity.getInstance().findViewById(R.id.rabbit)
        opponent = MainActivity.getInstance().findViewById(R.id.opponent)
        results = MainActivity.getInstance().findViewById(R.id.results)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //gets starting position of images
        if (start2) {
            startLion = MainActivity.getInstance().getStartLion()
            startCobra = MainActivity.getInstance().getStartCobra()
            startRabbit = MainActivity.getInstance().getStartRabbit()
            backgroundCoord = MainActivity.getInstance().getBackgroundCoord()
            playerCoord = MainActivity.getInstance().getPlayerCoord()
            opponentCoord = MainActivity.getInstance().getOpponentCoord()
            lionCoords = startLion
            cobraCoords = startCobra
            rabbitCoords = startRabbit
            start2 = false
        }

        //get x and y of mouse
        val x = event.x
        val y = event.y

        //if mouse press down
        if (event.action == MotionEvent.ACTION_DOWN ) {
            //if click was on lion
            if ( (x > lion?.left!!) && (x < lion?.right!!) && (y > lion?.top!!) && (y < lion?.bottom!!)) {

                //moves lion
                lionCoords = Rect(lion?.left!!,lion?.top!!,lion?.right!!,lion?.bottom!!)
                startLionPoint.set(x.toInt(),y.toInt())
                offsetLion.set((x - lionCoords.left).toInt(), (y - lionCoords.top).toInt())
                lion_touched = true

                //changes lion images
                val handler = Handler()
                var i = 0
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        lion!!.setImageResource(lionImages[i])
                        handler.postDelayed(this, 500)
                        i++
                        if (i > lionImages.size-1)
                            i = 0
                        if (!lion_touched) {
                            handler.removeCallbacks(this)
                            lion!!.setImageResource(lionImages[0])
                            lion!!.left = lionCoords.left
                            lion!!.right = lionCoords.right
                            lion!!.top = lionCoords.top
                            lion!!.bottom = lionCoords.bottom
                            invalidate()
                        }
                    }
                }, 0)
            }
            //if click was on cobra
            else if ((x > cobra?.left!!) && (x < cobra?.right!!) && (y > cobra?.top!!) && (y < cobra?.bottom!!)) {

                //moves cobra
                cobraCoords = Rect(cobra?.left!!,cobra?.top!!,cobra?.right!!,cobra?.bottom!!)
                startCobraPoint.set(x.toInt(),y.toInt())
                offsetCobra.set((x - cobraCoords.left).toInt(), (y - cobraCoords.top).toInt())
                cobra_touched = true
            }
            //if click was on rabbit
            else if ((x > rabbit?.left!!) && (x < rabbit?.right!!) && (y > rabbit?.top!!) && (y < rabbit?.bottom!!)) {

                //moves rabbit
                rabbitCoords = Rect(rabbit?.left!!,rabbit?.top!!,rabbit?.right!!,rabbit?.bottom!!)
                startRabbitPoint.set(x.toInt(),y.toInt())
                offsetRabbit.set((x - rabbitCoords.left).toInt(), (y - rabbitCoords.top).toInt())
                rabbit_touched = true
            }
        }
        //check if images are moving
        else if (event.action ==  MotionEvent.ACTION_MOVE) {

            //check if lion is touched
            if (lion_touched) {

                //moves lion and redraws
                lionCoords.left = x.toInt() - offsetLion.x
                lionCoords.top = y.toInt() - offsetLion.y
                lionCoords.right = (x + lion?.width!! - offsetLion.x).toInt()
                lionCoords.bottom = (y + lion?.height!! - offsetLion.y).toInt()
                this.invalidate()
            }
            //check if cobra is touched
            else if (cobra_touched) {

                //moves cobra and redraws
                cobraCoords.left = x.toInt() - offsetCobra.x
                cobraCoords.top = y.toInt() - offsetCobra.y
                cobraCoords.right = (x + cobra?.width!! - offsetCobra.x).toInt()
                cobraCoords.bottom = (y + cobra?.height!! - offsetCobra.y).toInt()
                this.invalidate()
            }
            //check if rabbit is touched
            else if (rabbit_touched) {

                //moves rabbit and redraws
                rabbitCoords.left = x.toInt() - offsetRabbit.x
                rabbitCoords.top = y.toInt() - offsetRabbit.y
                rabbitCoords.right = (x + rabbit?.width!! - offsetRabbit.x).toInt()
                rabbitCoords.bottom = (y + rabbit?.height!! - offsetRabbit.y).toInt()
                this.invalidate()
            }
        }

        //if left click up
        else if (event.action == MotionEvent.ACTION_UP) {

            //sets animations
            val myFadeIn = AnimationUtils.loadAnimation(MyView.getInstance().context, R.anim.fade_in)
            val fadeHandler = FadeInHandler()
            myFadeIn.setAnimationListener(fadeHandler)

            val myFadeOut = AnimationUtils.loadAnimation(MyView.getInstance().context, R.anim.fade_out)
            val fadeOutHandler = FadeOutHandler()
            myFadeOut.setAnimationListener(fadeOutHandler)
            myFadeOut.fillAfter = false

            //check if lion is touched
            if (lion_touched) {
                // check if image is in middle
                if (doOverlap(backgroundCoord, lionCoords)) {

                    //set image to player coords
                    lionCoords = playerCoord;
                    lion?.left = lionCoords.left
                    lion?.right = lionCoords.right
                    lion?.top = lionCoords.top
                    lion?.bottom = lionCoords.bottom
                    lion_touched = false

                    //get rival
                    val rival = opponents[(0..2).random()]
                    opponent?.setImageResource(rival)
                    opponent?.visibility = VISIBLE
                    results?.visibility = VISIBLE


                    //set tie animation
                    if (rival == R.drawable.lion0) {
                        opponent?.scaleX = -1.0f
                        results?.text = "Tie!"
                        opponent?.startAnimation(myFadeIn)
                        results?.startAnimation(myFadeIn)
                        myFadeOut.fillAfter = false
                        lion?.startAnimation(myFadeIn)
                        opponent?.startAnimation(myFadeOut)
                        lion?.startAnimation(myFadeOut)

                    }

                    //set lose animation
                    if (rival == R.drawable.cobra) {
                        opponent?.scaleX = -1.0f
                        results?.text = "Cobra defeats Lion - You Lose!"
                        opponent?.startAnimation(myFadeIn)
                        results?.startAnimation(myFadeIn)
                        lion?.startAnimation(myFadeOut)
                        val deltaX = (playerCoord.left-opponentCoord.left).toFloat()
                        val deltaY = (playerCoord.top-opponentCoord.top).toFloat()
                        val moveAnim = TranslateAnimation(0f, deltaX, 0f, deltaY)
                        moveAnim.duration = 2000
                        moveAnim.fillAfter = true
                        val handler = MoveHandler()
                        moveAnim.setAnimationListener(handler)
                        opponent?.startAnimation(moveAnim)
                        val score = MainActivity.getInstance().findViewById<TextView>(R.id.phoneScore)
                        score.text = "" + (Integer.parseInt(score.text.toString()) + 1)
                    }

                    //set win animation
                    if (rival == R.drawable.rabbit) {
                        opponent?.scaleX = 1.0f
                        results?.text = "Lion defeats Rabbit - You Win!"
                        val score = MainActivity.getInstance().findViewById<TextView>(R.id.playerScore)
                        score.text = "" + (Integer.parseInt(score.text.toString()) + 1)
                        opponent?.startAnimation(myFadeIn)
                        results?.startAnimation(myFadeIn)
                        val deltaX = (opponentCoord.left-playerCoord.left).toFloat()
                        val deltaY = (opponentCoord.top-playerCoord.top).toFloat()
                        val moveAnim = TranslateAnimation(0f, deltaX, 0f, deltaY)
                        moveAnim.duration = 2000
                        moveAnim.fillAfter = false
                        val handler = MoveHandler()
                        moveAnim.setAnimationListener(handler)
                        lion?.startAnimation(moveAnim)
                        opponent?.startAnimation(myFadeOut)
                    }
                }
                //reset position
                else {
                    lionCoords = startLion
                    lion?.left = lionCoords.left
                    lion?.right = lionCoords.right
                    lion?.top = lionCoords.top
                    lion?.bottom = lionCoords.bottom
                    lion_touched = false
                }
                this.invalidate()
            }
            //check if cobra is touched
            else if (cobra_touched) {
                // check if image is in middle
                if (doOverlap(backgroundCoord, cobraCoords)) {

                    //set image to player coords
                    cobraCoords = playerCoord;
                    cobra?.left = cobraCoords.left
                    cobra?.right = cobraCoords.right
                    cobra?.top = cobraCoords.top
                    cobra?.bottom = cobraCoords.bottom
                    cobra_touched = false

                    //get rival
                    val rival = opponents[(0..2).random()]
                    opponent?.setImageResource(rival)
                    opponent?.visibility = VISIBLE
                    results?.visibility = VISIBLE

                    //set win animation
                    if (rival == R.drawable.lion0) {
                        results?.text = "Cobra defeats Lion - You Win!"
                        val score = MainActivity.getInstance().findViewById<TextView>(R.id.playerScore)
                        score.text = "" + (Integer.parseInt(score.text.toString()) + 1)
                        opponent?.startAnimation(myFadeIn)
                        results?.startAnimation(myFadeIn)
                        val deltaX = (opponentCoord.left-playerCoord.left).toFloat()
                        val deltaY = (opponentCoord.top-playerCoord.top).toFloat()
                        val moveAnim = TranslateAnimation(0f, deltaX, 0f, deltaY)
                        moveAnim.duration = 2000
                        moveAnim.fillAfter = false
                        val handler = MoveHandler()
                        moveAnim.setAnimationListener(handler)
                        cobra?.startAnimation(moveAnim)
                        opponent?.startAnimation(myFadeOut)
                    }

                    //set tie animation
                    if (rival == R.drawable.cobra) {
                        opponent?.scaleX = -1.0f
                        results?.text = "Tie!"
                        opponent?.startAnimation(myFadeIn)
                        results?.startAnimation(myFadeIn)
                        cobra?.startAnimation(myFadeOut)
                        opponent?.startAnimation(myFadeOut)
                    }

                    //set lose animation
                    if (rival == R.drawable.rabbit) {
                        opponent?.scaleX = 1.0f
                        results?.text = "Rabbit defeats Cobra - You Lose!"
                        opponent?.startAnimation(myFadeIn)
                        results?.startAnimation(myFadeIn)
                        cobra?.startAnimation(myFadeOut)
                        val deltaX = (playerCoord.left-opponentCoord.left).toFloat()
                        val deltaY = (playerCoord.top-opponentCoord.top).toFloat()
                        val moveAnim = TranslateAnimation(0f, deltaX, 0f, deltaY)
                        moveAnim.duration = 2000
                        moveAnim.fillAfter = true
                        val handler = MoveHandler()
                        moveAnim.setAnimationListener(handler)
                        opponent?.startAnimation(moveAnim)
                        val score = MainActivity.getInstance().findViewById<TextView>(R.id.phoneScore)
                        score.text = "" + (Integer.parseInt(score.text.toString()) + 1)
                    }
                }
                //reset position
                else {
                    cobraCoords = startCobra
                    cobra?.left = cobraCoords.left
                    cobra?.right = cobraCoords.right
                    cobra?.top = cobraCoords.top
                    cobra?.bottom = cobraCoords.bottom
                    cobra_touched = false
                }
                this.invalidate()
            }
            //check if rabbit is touched
            else if (rabbit_touched) {
                // check if image is in middle
                if (doOverlap(backgroundCoord, rabbitCoords)) {

                    //set image to player coords
                    rabbitCoords = playerCoord;
                    rabbit?.left = rabbitCoords.left
                    rabbit?.right = rabbitCoords.right
                    rabbit?.top = rabbitCoords.top
                    rabbit?.bottom = rabbitCoords.bottom
                    rabbit?.scaleX = -1.0f
                    rabbit_touched = false

                    //get rival
                    val rival = opponents[(0..2).random()]
                    opponent?.setImageResource(rival)
                    opponent?.visibility = VISIBLE
                    results?.visibility = VISIBLE

                    //set lose animation
                    if (rival == R.drawable.lion0) {
                        opponent?.scaleX = -1.0f
                        results?.text = "Lion defeats Rabbit - You Lose!"
                        opponent?.startAnimation(myFadeIn)
                        results?.startAnimation(myFadeIn)
                        rabbit?.startAnimation(myFadeOut)
                        val deltaX = (playerCoord.left-opponentCoord.left).toFloat()
                        val deltaY = (playerCoord.top-opponentCoord.top).toFloat()
                        val moveAnim = TranslateAnimation(0f, deltaX, 0f, deltaY)
                        moveAnim.duration = 2000
                        moveAnim.fillAfter = true
                        val handler = MoveHandler()
                        moveAnim.setAnimationListener(handler)
                        opponent?.startAnimation(moveAnim)
                        val score = MainActivity.getInstance().findViewById<TextView>(R.id.phoneScore)
                        score.text = "" + (Integer.parseInt(score.text.toString()) + 1)
                    }

                    //set win animation
                    if (rival == R.drawable.cobra) {
                        opponent?.scaleX = -1.0f
                        results?.text = "Rabbit defeats Cobra - You Win!"
                        val score = MainActivity.getInstance().findViewById<TextView>(R.id.playerScore)
                        score.text = "" + (Integer.parseInt(score.text.toString()) + 1)
                        opponent?.startAnimation(myFadeIn)
                        results?.startAnimation(myFadeIn)
                        val deltaX = (opponentCoord.left-playerCoord.left).toFloat()
                        val deltaY = (opponentCoord.top-playerCoord.top).toFloat()
                        val moveAnim = TranslateAnimation(0f, deltaX, 0f, deltaY)
                        moveAnim.duration = 2000
                        moveAnim.fillAfter = false
                        val handler = MoveHandler()
                        moveAnim.setAnimationListener(handler)
                        rabbit?.startAnimation(moveAnim)
                        opponent?.startAnimation(myFadeOut)
                    }

                    //set tie animation
                    if (rival == R.drawable.rabbit) {
                        opponent?.scaleX = 1.0f
                        results?.text = "Tie!"
                        opponent?.startAnimation(myFadeIn)
                        results?.startAnimation(myFadeIn)
                        rabbit?.startAnimation(myFadeOut)
                        opponent?.startAnimation(myFadeOut)
                    }
                }
                //reset position
                else {
                    rabbitCoords = startRabbit
                    rabbit?.left = rabbitCoords.left
                    rabbit?.right = rabbitCoords.right
                    rabbit?.top = rabbitCoords.top
                    rabbit?.bottom = rabbitCoords.bottom
                    if (rabbit?.scaleX == -1.0f)
                        rabbit?.scaleX = 1.0f
                    rabbit_touched = false
                }
                this.invalidate()
            }
        }

        return true
    }

    /*********************Move Handler**********************************/
    inner class MoveHandler : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {

        }
        override fun onAnimationEnd(animation: Animation?) {

        }
        override fun onAnimationStart(animation: Animation?) {

        }
    }


    /**************************FadeInHandler**********************************************/
    inner class FadeInHandler : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {

        }
        override fun onAnimationEnd(animation: Animation?) {

        }
        override fun onAnimationStart(animation: Animation?) {

        }
    }

    /*********************FadeOutHandler**********************************/
    inner class FadeOutHandler : Animation.AnimationListener {
        override fun onAnimationRepeat(animation: Animation?) {

        }
        override fun onAnimationEnd(animation: Animation?) {
            opponent?.visibility = INVISIBLE
            results?.visibility = INVISIBLE
            if (lionCoords == playerCoord) {
                lionCoords = startLion
                lion?.left = lionCoords.left
                lion?.right = lionCoords.right
                lion?.top = lionCoords.top
                lion?.bottom = lionCoords.bottom
                invalidate()
                val myFadeOut = AnimationUtils.loadAnimation(MyView.getInstance().context, R.anim.fade_out)
                val fadeOutHandler = FadeOutHandler()
                myFadeOut.setAnimationListener(fadeOutHandler)
                results?.startAnimation(myFadeOut)
            }
            if (cobraCoords == playerCoord) {
                cobraCoords = startCobra
                cobra?.left = cobraCoords.left
                cobra?.right = cobraCoords.right
                cobra?.top = cobraCoords.top
                cobra?.bottom = cobraCoords.bottom
                invalidate()
            }
            if (rabbitCoords == playerCoord) {
                rabbitCoords = startRabbit
                rabbit?.left = rabbitCoords.left
                rabbit?.right = rabbitCoords.right
                rabbit?.top = rabbitCoords.top
                rabbit?.bottom = rabbitCoords.bottom
                if (rabbit?.scaleX == -1.0f)
                    rabbit?.scaleX = 1.0f
                invalidate()
            }
        }
        override fun onAnimationStart(animation: Animation?) {

        }
    }

    //check if rect overlap
    private fun doOverlap(rect1 : Rect, rect2 : Rect) : Boolean {
        if (rect1.left > rect2.right || rect2.left > rect1.right)
            return false
        if (rect1.top > rect2.bottom || rect2.top > rect1.bottom)
            return false
        return true
    }

}