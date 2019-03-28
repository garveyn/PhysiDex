package com.physidex.physidex

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DeckManagerPage : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.deck_manager_main, container, false)

        viewManager = LinearLayoutManager(context)
        viewAdapter = DeckAdapter(ArrayList<Deck>())

        recyclerView = view.findViewById<RecyclerView>(R.id.deck_recyclerView).apply {

            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }

        return view
    }




}