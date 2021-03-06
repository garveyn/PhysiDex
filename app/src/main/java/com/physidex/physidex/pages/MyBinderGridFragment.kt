package com.physidex.physidex.pages


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.adapters.DisplayCardAdapter
import com.physidex.physidex.database.daos.CardDao
import com.physidex.physidex.database.viewmodels.MyBinderViewModel

class MyBinderGridFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DisplayCardAdapter
    private lateinit var binderViewModel: MyBinderViewModel
    lateinit var deckCopies: List<CardDao.CopiesPerId>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?) : View {
        return inflater.inflate(R.layout.my_binder_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the Intent that started this activity and extract the string
        val safeArgs: MyBinderGridFragmentArgs by navArgs()
        val query = safeArgs.listToDisplay
        val deckId = safeArgs.deckID

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.binder_cards_view)
        adapter = DisplayCardAdapter(context!!) { index ->
            // when a card is selected, open detail fragment
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            val detail = CardDetailFragment()
            detail.detailedCard = adapter.cards[index]
            detail.deckId = deckId
            fragmentTransaction.replace(R.id.grid_constraintLayout, detail)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        // Set up view model
        Log.d("MY_BINDER_DECK", "Received deck id: $deckId")
        binderViewModel = ViewModelProviders.of(this,
                (requireActivity() as MainActivity).viewModelFactory {
                    MyBinderViewModel(this.requireActivity().application, deckId) })
                .get(MyBinderViewModel::class.java)

        if (deckId != -1){
            binderViewModel.deckCardCopies.observe(this, Observer { deckCCopies ->
                deckCCopies?.let {
                    adapter.updateResults(it, true)
                    deckCopies = it
                }
            })
        }


        when (query) {
            "ALL" -> {
                binderViewModel.allCards.observe(this, Observer { cards ->
                    cards?.let {
                        adapter.setResults(it)
                        // If this is displayed in the Deck manager, set up the number of copies per deck
                        if (::deckCopies.isInitialized) {
                            adapter.updateResults(deckCopies, true)
                        }
                    }
                })
            }
            "TRAINER" -> {
                binderViewModel.trainerCards.observe(this, Observer { cards ->
                    cards?.let {
                        adapter.setResults(it)
                        // If this is displayed in the Deck manager, set up the number of copies per deck
                        if (::deckCopies.isInitialized) {
                            adapter.updateResults(deckCopies, true)
                        }
                    }
                })
            }
            "RECENT" -> {
                binderViewModel.allCardsByDate.observe(this, Observer { cards ->
                    cards?.let {
                        adapter.setResults(it)
                        // If this is displayed in the Deck manager, set up the number of copies per deck
                        if (::deckCopies.isInitialized) {
                            adapter.updateResults(deckCopies, true)
                        }
                    }
                })
            }
            else -> {
                Log.d("BAD_INTENT", "query was not ALL or RECENT. Found $query")
                // if no query was set, display all.
                binderViewModel.allCards.observe(this, Observer { cards ->
                    cards?.let {
                        adapter.setResults(it)
                        // If this is displayed in the Deck manager, set up the number of copies per deck
                        if (::deckCopies.isInitialized) {
                            Log.d("DeckCopies", "deck copies is initialized")
                            adapter.updateResults(deckCopies, true)
                        }
                    }
                })
            }
        }
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