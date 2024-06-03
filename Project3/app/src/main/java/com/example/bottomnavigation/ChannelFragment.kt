package com.example.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Space
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class ChannelFragment : Fragment()
{
  var callLetters = mutableListOf("France24","Weather","CBS","Free Speech", "Travel", "SECDN", "Daytona", "HouseTV","ROKU","Orange County", "Seminole County",
    "University of Texas","University of California")
  var urls = mutableListOf("http://static.france24.com/live/F24_EN_LO_HLS/live_web.m3u8",
    "http://weather-lh.akamaihd.net/i/twc_1@92006/master.m3u8",
    "http://cbsnewshd-lh.akamaihd.net/i/CBSNHD_7@199302/master.m3u8",
    "https://edge.free-speech-tv-live.top.comcast.net/out/u/fstv.m3u8",
    "http://media4.tripsmarter.com:1935/LiveTV/ACVBHD/chucklist.m3u8",
    "http://na-all15.secdn.net/pegstream3-live/play/c3e1e4c4-7f11-4a54-8b8f-c590a95b4ade/playlist.m3u8",
    "http://oflash.dfw.swagit.com/live/daytonabeachfl/smil:std-4x3-1-a/chucklist.m3u8", "http://d3ktuc8v2sjk6m.cloudfront.net/livetv/ngrp:HouseChannel_all/chucklist.m3u8",
    "http://173.199.158.79:1935/roku/myStream/playlist.m3u8",
    "http://otv3.ocfl.net:1936/OrangeTV/smil:OrangeTV.smil/chunklist_w1007974604_b894100_sleng.m3u8",
    "http://video.seminolecountyfl.gov:1935/live/SGTV/chunklist.m3u8",
    "http://tstv-stream.tsm.utexas.edu/hls/livestream_hi/index.m3u8",
    "http://diffusionm4.assnat.qc.ca/canal9/250.sdp/playlist.m3u8"
  )

  companion object {

    private var instance: ChannelFragment? = null
    public fun getInstance(): ChannelFragment {
      return instance!!
    }
  }
  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    instance = this
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
  {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_channel, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    var linearLayout = MainActivity.getInstance().findViewById<LinearLayout>(R.id.linearlayout)

    var space: Space
    for (element in callLetters) {
      var button1 = View.inflate(MainActivity.getInstance(), R.layout.button, null) as Button

      var text = element

      button1.text = text

      var handler = ButtonHandler()
      button1.setOnClickListener(handler)

      linearLayout.addView(button1)

      var space = Space(MainActivity.getInstance())
      space.minimumHeight = 15

      linearLayout.addView(space)
    }
  }
}
class ButtonHandler : View.OnClickListener {
  override fun onClick(p0: View?) {
    var navController = Navigation.findNavController(ChannelFragment.getInstance().requireView())
    var button: Button
    var text: String
    var sendToBundle: String = ""

    // send to designated url from the channel selected
    if (p0 is Button) {
      button = p0
      text = button.text.toString()
      for ((i, element) in ChannelFragment.getInstance().callLetters.withIndex()) {
        if (text == element) {
          sendToBundle = ChannelFragment.getInstance().urls[i]
        }
      }
      var bundle: Bundle = Bundle()
      bundle.putString("stream", sendToBundle)
      //navController.navigate(R.id.channelToWeb, bundle)
    }
  }
}