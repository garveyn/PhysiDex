package com.physidex.physidex.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.adapters.DeckDetailAdapter
import com.physidex.physidex.database.viewmodels.DeckDetailViewModel
import com.physidex.physidex.testClasses.TestData

class DeckDetailActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
//    private lateinit var viewModel: DeckDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deck_manager_editing)

        val testData = TestData.buildDecks()

        viewManager = LinearLayoutManager(this)
        viewAdapter = DeckDetailAdapter(this)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {

            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }

//        viewModel = ViewModelProviders.of(this).get(DeckDetailViewModel::class.java)
//        viewModel.deckCards

    }

}