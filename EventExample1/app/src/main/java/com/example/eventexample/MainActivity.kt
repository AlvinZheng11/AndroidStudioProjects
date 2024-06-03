package com.example.eventexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.View
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.CompoundButton.*
import android.widget.SeekBar.*
import android.widget.TextView.*
import android.text.TextWatcher

class Handler : OnClickListener, OnCheckedChangeListener, OnSeekBarChangeListener,
  OnEditorActionListener, TextWatcher
{
  private var sliderLabel : TextView? = null

  override fun afterTextChanged(s: Editable?)
  {
    println("after " + s)
  }
  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
  {
    println("before: " + s)
  }
  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
  {
    println("on " + s)
  }


  override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean
  {
    println(v?.getText())
    //var im = MainActivity.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    //im.hideSoftInputFromWindow(v?.getWindowToken(),0)
    return true
  }


  override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
  {
    var slider = MainActivity.getInstance().findViewById<TextView>(R.id.freq)
    var text = progress.toString()
    slider.setText(text)
    println("Inside of onProgressChanged")
  }

  override fun onStartTrackingTouch(seekBar: SeekBar?)
  {
    println("start")
  }

  override fun onStopTrackingTouch(seekBar: SeekBar?)
  {
    println("stop")
  }


  override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean)
  {
    var text = buttonView?.getText()
    println("Is checked" + buttonView?.isChecked())
    println(text)
    if (text == "AM")
    {
      buttonView?.setText("FM")
    }
    else
    {
      buttonView?.setText("AM")
    }
  }


  override fun onClick(v: View?)
  {
    //Get the text – note the typecase to more specific
    var text = (v as Button).getText()

    if (text == "Start")
    {
      println("Start")

    }
    else if (text == "Pause")
    {
      println("Pause")
    }
  }
}





class MainActivity : AppCompatActivity()
{
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
    setContentView(R.layout.activity_main)

    instance = this

    //Instantiate Handler
    var handler = Handler()

//Register Handler with the Buttons
    var start = findViewById<Button>(R.id.start)
    var pause = findViewById<Button>(R.id.pause)
    var band = findViewById<CompoundButton>(R.id.band)
    var slider = findViewById<SeekBar>(R.id.seekBar)
    var dataEntry = findViewById<EditText>(R.id.dataEntry)
    start.setOnClickListener(handler)
    pause.setOnClickListener(handler)
    band.setOnCheckedChangeListener(handler)
    band.setOnClickListener(handler)
    slider.setOnSeekBarChangeListener(handler)
    dataEntry.setOnEditorActionListener(handler)
    dataEntry.addTextChangedListener(handler)


  }
}