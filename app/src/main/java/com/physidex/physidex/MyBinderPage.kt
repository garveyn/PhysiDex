package com.physidex.physidex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyBinderPage : Fragment() {

    private lateinit var recentRecyclerView: RecyclerView
    private lateinit var recentViewAdapter: RecyclerView.Adapter<*>
    private lateinit var recentViewManager: RecyclerView.LayoutManager

    private lateinit var mostUsedRecyclerView: RecyclerView
    private lateinit var mostUsedViewAdapter: RecyclerView.Adapter<*>
    private lateinit var mostUsedViewManager: RecyclerView.LayoutManager

    private lateinit var allRecyclerView: RecyclerView
    private lateinit var allViewAdapter: RecyclerView.Adapter<*>
    private lateinit var allViewManager: RecyclerView.LayoutManager

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.my_binder_main, container, false)

        recentViewManager = LinearLayoutManager(view.context)
        recentViewAdapter = DisplaySearchAdapter(view.context)

        recentRecyclerView

        return view
    }


}