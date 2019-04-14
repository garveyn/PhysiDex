package com.physidex.physidex.pages

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
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

        // Create Action bar
        val toolbar: Toolbar = findViewById(R.id.my_binder_toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            setBackgroundDrawable(ColorDrawable(
                    ContextCompat.getColor(this@MyBinderGridActivity, R.color.colorPrimary)
            ))
        }

        // Set up RecyclerView
        recyclerView = binder_cards_view
        adapter = DisplayCardAdapter(this) { index ->
            // when a card is selected, open detail fragment
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val detail = CardDetailFragment()
            detail.detailedCard = adapter.cards[index]
            fragmentTransaction.replace(R.id.grid_constraintLayout, detail)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
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
                actionbar?.title = getString(R.string.binder_all)
            }
            "RECENT" -> {
                binderViewModel.allCardsByDate.observe(this, Observer { cards ->
                    cards?.let { adapter.setResults(it)}
                })
                actionbar?.title = getString(R.string.binder_recent)
            }
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem) : Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(menuItem)
            }
        }
    }


}