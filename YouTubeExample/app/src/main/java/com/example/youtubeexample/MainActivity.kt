package com.example.youtubeexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import org.json.JSONObject
import java.net.URL

class UIThreadHelper(private var video: String) : Runnable
{
    override fun run()
    {
        //Update the webView
        val web = MainActivity.getInstance().findViewById<WebView>(R.id.web)
        val settings = web.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.minimumFontSize = 10
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        web.isVerticalScrollBarEnabled = false
        settings.domStorageEnabled = true
        web.webViewClient = WebViewClient()
        val str = "https://www.youtube.com/watch?v=$video"
        web.loadUrl(str)

    }
}

class Helper(private var url: String, private var song: String, private var artist: String) : Runnable
{

    override fun run()
    {
        val data = URL(url).readText()
        println(data)

        val json = JSONObject(data)
        val items = json.getJSONArray("items")

        val titles = ArrayList<String>()
        val videos = ArrayList<String>()

        for (i in 0 until items.length())
        {
            val videoObject = items.getJSONObject(i)
            //val title = videoObject.getString("title")
            //val videoId = videoObject.getString("id")
            println(videoObject)
            val idDict = videoObject.getJSONObject("id")
            println(idDict)
            val videoId = idDict.getString("videoId")
            println(videoId)
            val snippetDict = videoObject.getJSONObject("snippet")
            val title =  snippetDict.getString("title")
            println(title)
            titles.add(title)
            videos.add(videoId)
        }
        var selectedTitle : String = titles[33]
        val selectedVideo : String = videos[33]

        val helper1 = UIThreadHelper(selectedVideo)
        MainActivity.getInstance().runOnUiThread(helper1)


    }

}

class MainActivity : AppCompatActivity() {
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

        var song = "Please Please Me"
        song = song.replace(" ", "+")
        val origSong = "Please Please Me"

        //Set the artist
        var artist = "The Beatles"
        artist = artist.replace(" ","+")
        val origArtist = "The Beatles"
        //Encode search for YouTube
        val keywords = "$artist+$song"
        val max = 50

        //Set the youtube search
        //Set the youtube search
        val string = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=$keywords&order=viewCount&maxResults=$max&type=video&videoCategory=Music&key=AIzaSyDtzKWgA0ne39VD_-i0oJwCd4WOdFKZy4I"

        val helper = Helper(string, origSong, origArtist)
        val thread = Thread(helper)
        thread.start()

    }
}