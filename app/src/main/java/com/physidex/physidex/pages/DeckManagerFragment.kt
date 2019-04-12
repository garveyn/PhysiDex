package com.physidex.physidex.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.physidex.physidex.R
import com.physidex.physidex.adapters.DeckAdapter
import com.physidex.physidex.database.viewmodels.DeckManagerViewModel
import com.physidex.physidex.decorations.SeparatorItemDecoration
import com.physidex.physidex.testClasses.TestData

class DeckManagerFragment : Fragment(), View.OnClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: DeckAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var itemDecoration: SeparatorItemDecoration
    private lateinit var dmViewModel: DeckManagerViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.deck_manager_main, container, false)

        val testData = TestData.buildDecks()

        viewManager = LinearLayoutManager(context)
        viewAdapter = DeckAdapter()
        itemDecoration = SeparatorItemDecoration(resources.getDimension(R.dimen.margin_16dp).toInt())

        recyclerView = view.findViewById<RecyclerView>(R.id.deck_recyclerView).apply {

            setHasFixedSize(true)

            addItemDecoration(itemDecoration)

            layoutManager = viewManager

            adapter = viewAdapter
        }

        // Initialize view model
        dmViewModel = ViewModelProviders.of(this).get(DeckManagerViewModel::class.java)
        dmViewModel.allDecks.observe(this, Observer { decks ->
            decks?.let { viewAdapter.decks = decks }
        })

        // New Deck Button
        var button: Button = view.findViewById(R.id.new_deck)
        button.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        // do something in response to the button
        val editText = getView()!!.findViewById<EditText>(R.id.editText)

    }

}