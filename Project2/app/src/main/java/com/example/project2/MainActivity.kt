package com.example.project2

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class Handler : View.OnClickListener, OnCheckedChangeListener, OnSeekBarChangeListener {

    private val imageView2: ImageView = MainActivity.getInstance().findViewById<ImageView>(R.id.imageView2)
    private val band: Switch = MainActivity.getInstance().findViewById<Switch>(R.id.band)
    private val nameBox: TextView = MainActivity.getInstance().findViewById<TextView>(R.id.textView)
    private val webView: WebView = MainActivity.getInstance().findViewById<WebView>(R.id.webView)

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        var slider = MainActivity.getInstance().findViewById<TextView>(R.id.freq)
        var text = progress.toString()
        slider.text = text

        //sets text if no internet
        if (nameBox.text == "No Internet") {
            nameBox.text = "No Internet"
        }
        else if (text == "29" && (band.text == "AM")) { // WFAN Image
            val id = MainActivity.getInstance().resources.getIdentifier("wfan","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "WFAN"
        }
        else if (text == "41" && (band.text == "AM")) { // KABC image
            val id = MainActivity.getInstance().resources.getIdentifier("kabc","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "KABC"
        }
        else if (text == "45" && (band.text == "AM")) { // WBAP image
            val id = MainActivity.getInstance().resources.getIdentifier("wbap","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "WBAP"
        }
        else if (text == "50" && (band.text == "AM")) { // WLS image
            val id = MainActivity.getInstance().resources.getIdentifier("wls","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "WLS"
        }
        else if (text == "52" && (band.text == "AM")) { // KARN AM image
            val id = MainActivity.getInstance().resources.getIdentifier("karnam","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "KARN AM"
        }
        else if (text == "45" && (band.text == "FM")) { // WXYT image
            val id = MainActivity.getInstance().resources.getIdentifier("wxyt","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "WXYT"
        }
        else if (text == "51" && (band.text == "FM")) { // KURB image
            val id = MainActivity.getInstance().resources.getIdentifier("kurb","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "KURB"
        }
        else if (text == "52" && (band.text == "FM")) { // WKIM image
            val id = MainActivity.getInstance().resources.getIdentifier("wkim","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "WKIM"
        }
        else if (text == "56" && (band.text == "FM")) { // WWTN image
            val id = MainActivity.getInstance().resources.getIdentifier("wwtn","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "WWTN"
        }
        else if (text == "64" && (band.text == "FM")) { // KDXE image
            val id = MainActivity.getInstance().resources.getIdentifier("kdxe","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "KDXE"
        }
        else if (text == "70" && (band.text == "FM")) { // KARN FM image
            val id = MainActivity.getInstance().resources.getIdentifier("karnfm","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "KARN FM"
        }
        else if (text == "90" && (band.text == "FM")) { // KLAL image
            val id = MainActivity.getInstance().resources.getIdentifier("klal","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "KLAL"
        }
        else { // reset launch image
            val id = MainActivity.getInstance().resources.getIdentifier("launch","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "Now Playing"
        }
        println("Inside of onProgressChanged$text")
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        println("start")
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        println("stop")
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        var text = buttonView?.text
        println("Is checked" + buttonView?.isChecked)
        println(text)

        //check if it is AM or FM
        if (text == "AM") {
            buttonView?.text = "FM"
            //reset images and text to default
            val id = MainActivity.getInstance().resources.getIdentifier("launch","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "Now Playing"
        } else {
            buttonView?.text = "AM"
            //reset images and text to default
            val id = MainActivity.getInstance().resources.getIdentifier("launch","drawable",MainActivity.getInstance().packageName)
            imageView2.setImageResource(id)
            nameBox.text = "Now Playing"
        }
    }

    override fun onClick(v: View?) {
        //Get the text – note the type case to more specific
        var text = (v as Button).text

        //opens links for stations
        if (text == "PLAY" && (nameBox.text == "WFAN")) { //opens WFAN
            webView.loadUrl("http://playerservices.streamtheworld.com/api/livestream-redirect/WFANAM.mp3")
        }
        else if (text == "PLAY" && (nameBox.text == "KABC")) { //opens KABC
            webView.loadUrl("http://playerservices.streamtheworld.com/api/livestream-redirect/KABCAM.mp3")
        }
        else if (text == "PLAY" && (nameBox.text == "WBAP")) { //opens WBAP
            webView.loadUrl("http://playerservices.streamtheworld.com/api/livestream-redirect/WBAPAM.mp3")
        }
        else if (text == "PLAY" && (nameBox.text == "WLS")) { //opens WLS
            webView.loadUrl("http://playerservices.streamtheworld.com/api/livestream-redirect/WLSAM.mp3")
        }
        else if (text == "PLAY" && (nameBox.text == "KARN AM")) { //opens KARN AM
            webView.loadUrl("http://playerservices.streamtheworld.com/api/livestream-redirect/KARNAM.mp3")
        }
        else if (text == "PLAY" && (nameBox.text == "WXYT")) { //opens WXYT
            webView.loadUrl("http://playerservices.streamtheworld.com/api/livestream-redirect/WXYTFM.mp3")
        }
        else if (text == "PLAY" && (nameBox.text == "KURB")) { //opens KABC
            webView.loadUrl("http://playerservices.streamtheworld.com/api/livestream-redirect/KURBFM.mp3")
        }
        else if (text == "PLAY" && (nameBox.text == "WKIM")) { //opens WKIM
            webView.loadUrl("http://playerservices.streamtheworld.com/api/livestream-redirect/WKIMFM.mp3")
        }
        else if (text == "PLAY" && (nameBox.text == "WWTN")) {  //opens WWTN
            webView.loadUrl("http://playerservices.streamtheworld.com/api/livestream-redirect/WWTNFM.mp3")
        }
        else if (text == "PLAY" && (nameBox.text == "KDXE")) {  //opens KDXE
            webView.loadUrl("http://playerservices.streamtheworld.com/api/livestream-redirect/KDXEFM.mp3")
        }
        else if (text == "PLAY" && (nameBox.text == "KARN FM")) { //opens KARN FM
            webView.loadUrl("http://playerservices.streamtheworld.com/api/livestream-redirect/KARNFM.mp3")
        }
        else if (text == "PLAY" && (nameBox.text == "KLAL")) { //opens KLAL
            webView.loadUrl("http://playerservices.streamtheworld.com/api/livestream-redirect/KLALFM.mp3")
        }

    }

}

class MainActivity : AppCompatActivity() {
    private var webView : WebView? = null
    companion object {
        private var instance: MainActivity? = null

        public fun getInstance(): MainActivity {
            return instance!!
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instance = this

        //Instantiate Handler
        var handler = Handler()

        //Checks if has internet
        var res = isNetworkAvailable(this)
        if(!res) {
            val dialogBuilder = AlertDialog.Builder(this)
            //set message
            dialogBuilder.setMessage("No Internet Connection")
                //set button
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, id ->
                    dialog.dismiss()
                    // change text to No Internet
                    val nameBox: TextView = MainActivity.getInstance().findViewById<TextView>(R.id.textView)
                    nameBox.text = "No Internet"
                }

            val alert = dialogBuilder.create()
            //set title
            alert.setTitle("Failure")
            alert.show()
        }

//Register Handler with the Buttons
        var play = findViewById<Button>(R.id.play)
        var band = findViewById<CompoundButton>(R.id.band)
        var slider = findViewById<SeekBar>(R.id.seekBar)
        play.setOnClickListener(handler)
        band.setOnCheckedChangeListener(handler)
        band.setOnClickListener(handler)
        slider.setOnSeekBarChangeListener(handler)

    }

}

fun isNetworkAvailable(context: Context): Boolean
{
    var connectivityManager =(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
    {
        val network = connectivityManager.activeNetwork // network is currently in a high power state for performing data transmission.

        Log.d("Network", "active network $network")
        network ?: return false // return false if network is null

        val actNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false // return false if Network Capabilities is null

        return when
        {
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> { // check if wifi is connected
                Log.d("Network", "wifi connected")
                true
            }
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> { // check if mobile dats is connected
                Log.d("Network", "cellular network connected")
                true
            }
            else -> {
                Log.d("Network", "internet not connected")
                false
            }
        }
    }
    return false
}