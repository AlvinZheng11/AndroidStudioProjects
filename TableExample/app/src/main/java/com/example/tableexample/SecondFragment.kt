package com.example.tableexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SecondFragment : Fragment() {

    private var titles = arrayOf("Jim's Schedule", "Mary's Schedule", "Jo's Schedule")
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterSchedule.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var arguments = this.getArguments()
        var pos = arguments?.getInt("position")

        layoutManager = LinearLayoutManager(MainActivity.getInstance())

        var recycler_view2 = MainActivity.getInstance().findViewById<RecyclerView>(R.id.recycler_view2)


        recycler_view2.layoutManager = layoutManager
        adapter = RecyclerAdapterSchedule(pos!!) //this is the desired contact selected
        recycler_view2.adapter = adapter

    }
}