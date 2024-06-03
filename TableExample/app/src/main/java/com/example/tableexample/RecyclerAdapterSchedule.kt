package com.example.tableexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapterSchedule  : RecyclerView.Adapter<RecyclerAdapterSchedule.ViewHolder>
{
    private var pos : Int = -1
    private var titles = arrayOf("Jim's Schedule", "Mary's Schedule", "Jo's Schedule")
    private var schedule = arrayOf<Array<String>>()
    private var jimSchedule = arrayOf("CS1: 9:00 - 9:50\n2-D arrays and intro to pointers","Data Structures: 10:00 - 10:50\nBinary Trees","Office Hours: 11:00 -3:00 pm")
    private var marySchedule = arrayOf("Intro to Computers: 10:00 -10:50\nReview MS Word","Office Hours: 11:00 - 12:00","Lunch time: 12:00 - 1:00")
    private var joSchedule = arrayOf("Algorithms: 10:00 - 10:50\nReview for Test #2", "Meeting with local company", "Meeting with high school students")
    private var days = arrayOf("Monday", "Tuesday", "Wednesday")

    constructor(pos : Int) : super()
    {
        this.pos = pos
    }

    init
    {
        schedule += jimSchedule
        schedule += marySchedule
        schedule += joSchedule
    }
    public fun setPos(pos : Int)
    {
        this.pos = pos
    }
    override fun onBindViewHolder(holder: RecyclerAdapterSchedule.ViewHolder, position: Int)
    {
        holder.itemScheduleHeading.text = days[position]
        holder.itemSchedule.text = schedule[pos][position]
    }
    override fun getItemCount(): Int
    {
        return schedule[pos].size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterSchedule.ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.schedule_card_layout, parent, false)
        return ViewHolder(v)
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var itemScheduleHeading: TextView
        var itemSchedule: TextView
        init
        {
            itemScheduleHeading = itemView.findViewById(R.id.scheduleHeading)
            itemSchedule = itemView.findViewById(R.id.schedule)
        }
    }
}
