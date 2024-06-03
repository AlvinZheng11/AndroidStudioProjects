package com.example.bottomnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient

class WebView : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        var webViewFragment = MainActivity.getInstance().findViewById<View>(R.id.webViewFragment)
//        var arguments = this.arguments
//        var text = arguments?.getString("stream")
//        val settings = webViewFragment.getSettings()
//        settings.setJavaScriptEnabled(true)
//        settings.setDomStorageEnabled(true)
//        settings.setMinimumFontSize(10)
//        settings.setLoadWithOverviewMode(true)
//        settings.setUseWideViewPort(true)
//        settings.setBuiltInZoomControls(true)
//        settings.setDisplayZoomControls(false)
//        webViewFragment.setVerticalScrollBarEnabled(false)
//        settings.setDomStorageEnabled(true)
//        webViewFragment.setWebChromeClient(WebChromeClient())
//        if (text != null) {
//            webViewFragment.loadUrl(text.toString())
//        }

    }
}