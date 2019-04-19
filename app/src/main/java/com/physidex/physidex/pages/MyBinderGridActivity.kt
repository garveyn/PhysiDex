package com.physidex.physidex.pages

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.adapters.DisplayCardAdapter
import com.physidex.physidex.database.viewmodels.MyBinderViewModel
import kotlinx.android.synthetic.main.drawer_test.*

class MyBinderGridActivity : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DisplayCardAdapter
    private lateinit var binderViewModel: MyBinderViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?) : View {

        val view = inflater.inflate(R.layout.my_binder_grid, container, false)


        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.binder_cards_view)
        adapter = DisplayCardAdapter(context!!) { index ->
            // when a card is selected, open detail fragment
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            val detail = CardDetailFragment()
            detail.detailedCard = adapter.cards[index]
            fragmentTransaction.replace(R.id.grid_constraintLayout, detail)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        // Set up view model
        binderViewModel = ViewModelProviders.of(this).get(MyBinderViewModel::class.java)

        // Get the Intent that started this activity and extract the string
        val safeArgs: MyBinderGridActivityArgs by navArgs()
        val query = safeArgs.listToDisplay
        val deckId = safeArgs.deckID

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
            else -> {
                Log.d("BAD_INTENT", "query was not ALL or RECENT. Found $query")
            }
        }
        return view
    }

    override fun onOptionsItemSelected(menuItem: MenuItem) : Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                requireFragmentManager().popBackStack()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(menuItem)
            }
        }
    }


}