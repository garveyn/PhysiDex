package com.physidex.physidex

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DeckManagerPage : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var itemDecoration: SeparatorItemDecoration

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.deck_manager_main, container, false)

        val testData = ArrayOfDecks.buildDecks()

        viewManager = LinearLayoutManager(context)
        viewAdapter = DeckAdapter(testData)
        itemDecoration = SeparatorItemDecoration(resources.getDimension(R.dimen.fab_margin).toInt())

        recyclerView = view.findViewById<RecyclerView>(R.id.deck_recyclerView).apply {

            setHasFixedSize(true)

            addItemDecoration(itemDecoration)

            layoutManager = viewManager

            adapter = viewAdapter
        }

        return view
    }




}