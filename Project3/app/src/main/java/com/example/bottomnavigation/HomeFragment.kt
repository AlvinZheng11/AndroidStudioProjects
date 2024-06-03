package com.example.bottomnavigation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.os.Handler
import android.os.Looper

class HomeFragment : Fragment() {
  companion object {

    private var instance: HomeFragment? = null
    public fun getInstance(): HomeFragment {
      return instance!!
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    instance = this

  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_home, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    //val slide = SlideShow()
    //slide.start()
  }
}

class SlideShow : Thread {

  private var noSlides = 0
  private var duration: Int = 0
  private var count: Int = 0
  private var imageView: ImageView? = null

  constructor() {
    duration = 5
    noSlides = 3
    imageView = MainActivity.getInstance().findViewById<ImageView>(R.id.imageView)
  }

  override fun run() {
    var files = arrayOf("homeimage", "image1", "image2")
    var handler1 = SlideHandler(files[count % files.size])
    while (true) {

      // Display "What Flag Is This?"
      handler1 = SlideHandler(files[count % files.size])
      MainActivity.getInstance().runOnUiThread(handler1)
      sleep(duration * 1000L)  // Display for 'duration' seconds

      count++
    }
  }
}

class SlideHandler : Runnable {
  private var fn: String = ""
  private var imageView: ImageView? = null
  private var id: Int? = null

  constructor(fn: String) {
    this.fn = fn
    imageView = MainActivity.getInstance().findViewById<ImageView>(R.id.imageView)

    id = MainActivity.getInstance().resources.getIdentifier(
      fn,
      "drawable",
      MainActivity.getInstance().packageName
    )
  }

  override fun run() {
    imageView?.setImageResource(id!!)
  }
}

