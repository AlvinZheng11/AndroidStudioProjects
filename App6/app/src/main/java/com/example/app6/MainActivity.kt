package com.example.app6

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    //starting positions of the images
    private var startLion = Rect(0,0,0,0)
    private var startCobra = Rect(0,0,0,0)
    private var startRabbit = Rect(0,0,0,0)
    private var backgroundCoord = Rect(0,0,0,0)
    private var playerCoord = Rect(0, 0, 0, 0)
    private var opponentCoord = Rect(0, 0, 0, 0)
    private var imageView : ImageView? = null

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
        val player = findViewById<ImageView>(R.id.player)
        val phoneText = findViewById<TextView>(R.id.phoneText)
        val phoneScore = findViewById<TextView>(R.id.phoneScore)
        val playerText = findViewById<TextView>(R.id.playerText)
        val playerScore = findViewById<TextView>(R.id.playerScore)
        val titleImage = findViewById<ImageView>(R.id.titleImage)

        player.visibility = View.INVISIBLE
        phoneText.visibility = View.INVISIBLE
        phoneScore.visibility = View.INVISIBLE
        playerText.visibility = View.INVISIBLE
        playerScore.visibility = View.INVISIBLE

        // start title zoom
        var myZoom = AnimationUtils.loadAnimation(getInstance(), R.anim.zoom_in)
        var zoomHandler = ZoomHandler()
        myZoom.setAnimationListener(zoomHandler)
        titleImage.startAnimation(myZoom)

        //Set the imageView once
        imageView = ImageView(this)

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
            val phoneText = findViewById<TextView>(R.id.phoneText)
            val phoneScore = findViewById<TextView>(R.id.phoneScore)
            val playerText = findViewById<TextView>(R.id.playerText)
            val playerScore = findViewById<TextView>(R.id.playerScore)

            println("Fade out finshied")
            var myFadeIn = AnimationUtils.loadAnimation(getInstance(), R.anim.fade_in)
            var fadeHandler = FadeInHandler()
            myFadeIn.setAnimationListener(fadeHandler)
            phoneScore.startAnimation(myFadeIn)
            phoneText.startAnimation(myFadeIn)
            playerScore.startAnimation(myFadeIn)
            playerText.startAnimation(myFadeIn)

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
            val phoneText = findViewById<TextView>(R.id.phoneText)
            val phoneScore = findViewById<TextView>(R.id.phoneScore)
            val playerText = findViewById<TextView>(R.id.playerText)
            val playerScore = findViewById<TextView>(R.id.playerScore)

            println("Fade in finshied")
            phoneText.visibility = View.VISIBLE
            phoneScore.visibility = View.VISIBLE
            playerText.visibility = View.VISIBLE
            playerScore.visibility = View.VISIBLE

        }
        override fun onAnimationStart(animation: Animation?)
        {
                 }
    }
    /**********************************************************************************/

    /***********************************Zoom in Handler**********************************/

    inner class ZoomHandler : Animation.AnimationListener
    {
        override fun onAnimationRepeat(animation: Animation?)
        {
            println("repeat")
        }
        override fun onAnimationEnd(animation: Animation?)
        {
            val titleImage = findViewById<ImageView>(R.id.titleImage)

            //title fade out
            var myFadeOut = AnimationUtils.loadAnimation(MainActivity.getInstance(), R.anim.fade_out)
            var fadeHandler = FadeOutHandler()
            myFadeOut.setAnimationListener(fadeHandler)
            titleImage.startAnimation(myFadeOut)
        }
        override fun onAnimationStart(animation: Animation?)
        {
            val lion = findViewById<ImageView>(R.id.lion)
            val cobra = findViewById<ImageView>(R.id.cobra)
            val rabbit = findViewById<ImageView>(R.id.rabbit)
            val frame = findViewById<ImageView>(R.id.frame)
            val player = findViewById<ImageView>(R.id.player)
            val opponent = findViewById<ImageView>(R.id.opponent)

            //set starting positions of images
            setStartLion(Rect(lion.left,lion.top,lion.right, lion.bottom))
            setOrigTouchedCobra(Rect(cobra.left,cobra.top,cobra.right, cobra.bottom))
            setStartRabbit(Rect(rabbit.left,rabbit.top,rabbit.right, rabbit.bottom))
            setBackgroundCoord(Rect(frame.left, frame.top, frame.right, frame.bottom))
            setPlayerCoord(Rect(player.left, player.top, player.right, player.bottom))
            setOpponentCoord(Rect(opponent.left, opponent.top, opponent.right, opponent.bottom))
        }
    }
    /*************************************************************************************************/

    // setters and getters for starting position of images
    fun setStartLion(startLion : Rect) {
        this.startLion = startLion
    }

    fun getStartLion() : Rect {
        return startLion
    }

    fun setOrigTouchedCobra(startCobra : Rect) {
        this.startCobra = startCobra
    }

    fun getStartCobra() : Rect {
        return startCobra
    }

    fun setStartRabbit(startRabbit : Rect) {
        this.startRabbit = startRabbit
    }

    fun getStartRabbit() : Rect {
        return startRabbit
    }

    fun setBackgroundCoord(backgroundCoord : Rect) {
        this.backgroundCoord = backgroundCoord
    }

    fun getBackgroundCoord() : Rect {
        return backgroundCoord
    }

    fun setPlayerCoord(playerCoord : Rect) {
        this.playerCoord = playerCoord
    }

    fun getPlayerCoord() : Rect {
        return playerCoord
    }

    fun setOpponentCoord(opponentCoord : Rect) {
        this.opponentCoord = opponentCoord
    }

    fun getOpponentCoord() : Rect {
        return opponentCoord
    }
}