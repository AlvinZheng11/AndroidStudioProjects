package com.example.tableexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class Contacts
{
    private var name : String = ""
    private var title : String = ""
    private var pic : String = ""

    constructor(name : String, title : String, pic : String)
    {
        this.name = name
        this.title = title
        this.pic = pic
    }

    public fun setName(name : String)
    {
        this.name = name
    }

    public fun getName() : String
    {
        return name
    }

    public fun getTitle() : String
    {
        return title
    }

    public fun setTitle(title : String)
    {
        this.title = title
    }

    public fun setPic(pic : String)
    {
        this.pic = pic
    }

    public fun getPic() : String
    {
        return pic
    }

}

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()
{
    private var people = arrayOf(Contacts("Jim", "Asst. Professor", "pic1"), Contacts("Mary", "Lecturer", "pic2"),
        Contacts("Jo", "Administrator", "pic3"))

    override fun getItemCount(): Int
    {
        return people.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.itemTitle.text = people[position].getName()
        holder.itemDetail.text = people[position].getTitle()
        holder.itemImage.setImageResource(MainActivity.getInstance().resources.getIdentifier(people[position].getPic(),"drawable",
            MainActivity.getInstance().packageName))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init
        {
            itemImage = itemView.findViewById(R.id.imageView)
            itemTitle = itemView.findViewById(R.id.name)
            itemDetail = itemView.findViewById(R.id.title)
            var handler = Handler()
            itemView.setOnClickListener(handler)

        }
        inner class Handler() : View.OnClickListener
        {
            override fun onClick(v: View?)
            {
                val itemPosition = getLayoutPosition()
                //Get the navigation controller
                var navController = Navigation.findNavController(MainFragment.getInstance().requireView()!!)
                val bundle = Bundle()
                bundle.putInt("position", itemPosition)
                navController.navigate(R.id.mainToSecond,bundle)
            }
        }

    }
}
