package com.example.dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface.*
import android.content.DialogInterface.OnClickListener
import android.webkit.WebViewClient
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebResourceResponse
import android.webkit.WebResourceRequest
import android.webkit.WebResourceError
import android.graphics.Bitmap


class Handler : OnClickListener
{

  override fun onClick(dialog: DialogInterface?, which: Int)
  {
    if (which == DialogInterface.BUTTON_NEGATIVE)
    {
      println("negative")
    }
    else if (which == DialogInterface.BUTTON_POSITIVE)
    {
      println("positive")
    }
  }

}


fun isNetworkAvailable(context: Context): Boolean
{
  var connectivityManager =(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    val network = connectivityManager.activeNetwork // network is currently in a high power state for performing data transmission.
    Log.d("Network", "active network $network")
    network ?: return false // return false if network is null
    val actNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false // return false if Network Capabilities is null
    return when {
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


fun isCapableNetwork(cm: ConnectivityManager,network: Network?): Boolean{
  cm.getNetworkCapabilities(network)?.also {
    if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
      return true
    } else if (it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
      return true
    }
  }
  return false
}


class MainActivity : AppCompatActivity()
{
  private var webView : WebView? = null

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    webView = findViewById<WebView>(R.id.webView)

    var delegate = Delegate()
    webView?.webViewClient = delegate

    //This will allow the tracing of links
    webView?.getSettings()?.setJavaScriptEnabled(true);
    webView?.getSettings()?.setJavaScriptCanOpenWindowsAutomatically(true);


    //webView?.loadUrl("http://www.uca.edu")
    webView?.loadUrl("http://playerservices.streamtheworld.com/api/livestream-redirect/KLALFM.mp3")

    var res = isNetworkAvailable(this)
    println(res)

    val dialogBuilder = AlertDialog.Builder(this)

    dialogBuilder.setMessage("Not Connected To Internet")

    //Instantiate the Handler object
    var handler = Handler()

    dialogBuilder.setPositiveButton("OK",handler)
    dialogBuilder.setNegativeButton("Cancel", handler)

    val alert1 = dialogBuilder.create()

    alert1.setTitle("No Internet")

    alert1.show()
    val items = arrayOf("Start", "Stop", "Connect", "Submit")
    val dialogBuilder1 = AlertDialog.Builder(this)
    dialogBuilder1.setItems(items, handler)
    val alert = dialogBuilder1.create()
    alert.setTitle("Internet Connected Title")
    //alert.show()

  }

  override fun onBackPressed()
  {
    if (webView!!.canGoBack())
    {
      webView?.goBack()
    }
  }

}

//Delegate class for handling
class Delegate : WebViewClient()
{
  override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
  {
    super.onPageStarted(view, url, favicon)
    println("started")
  }
  override fun onPageFinished(view: WebView, url: String)
  {
    super.onPageFinished(view,url)
    println("finish")
  }
  override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError)
  {
    println(error.description )
  }
  override fun onReceivedHttpError(
    view: WebView, request: WebResourceRequest, errorResponse: WebResourceResponse
  )
  {
    println(errorResponse.data)
  }
  override fun onReceivedSslError(
    view: WebView, handler: SslErrorHandler,
    error: SslError
  )
  {
    println(error.primaryError)
  }
}
