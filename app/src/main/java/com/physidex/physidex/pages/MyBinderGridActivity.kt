package com.physidex.physidex.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.adapters.DisplayCardAdapter
import com.physidex.physidex.database.viewmodels.MyBinderViewModel
import kotlinx.android.synthetic.main.my_binder_grid.*

class MyBinderGridActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DisplayCardAdapter
    private lateinit var binderViewModel: MyBinderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_binder_grid)

        // Set up RecyclerView
        recyclerView = binder_cards_view
        adapter = DisplayCardAdapter(this) { index ->
            // open detail fragment
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        // Set up view model
        binderViewModel = ViewModelProviders.of(this).get(MyBinderViewModel::class.java)

        // Get the Intent that started this activity and extract the string
        val query = intent.getStringExtra(MY_BINDER_CARDS)

        when (query) {
            "ALL" -> {
                binderViewModel.allCards.observe(this, Observer { cards ->
                    cards?.let { adapter.setResults(it)}
                })
            }
            "RECENT" -> {
                binderViewModel.allCardsByDate.observe(this, Observer { cards ->
                    cards?.let { adapter.setResults(it)}
                })
            }
        }
    }


}