package com.physidex.physidex.pages

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.adapters.DisplayCardAdapter
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.viewmodels.MyBinderViewModel

class MyBinderHomeFragment : Fragment() {

    private lateinit var recentCards:           List<FullPokeCard>
    private lateinit var recentRecyclerView:    RecyclerView
    private lateinit var recentViewAdapter: DisplayCardAdapter
    private lateinit var recentViewManager:     RecyclerView.LayoutManager

    private lateinit var mostUsedCards:         List<FullPokeCard>
    private lateinit var mostUsedRecyclerView:  RecyclerView
    private lateinit var mostUsedViewAdapter: DisplayCardAdapter
    private lateinit var mostUsedViewManager:   RecyclerView.LayoutManager

    private lateinit var allCards:              List<FullPokeCard>
    private lateinit var allRecyclerView:       RecyclerView
    private lateinit var allViewAdapter: DisplayCardAdapter
    private lateinit var allViewManager:        RecyclerView.LayoutManager

    private lateinit var binderViewModel: MyBinderViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.my_binder_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recentViewManager = LinearLayoutManager(view.context)
        recentViewAdapter = DisplayCardAdapter(view.context) {}

        // There are three different RecyclerViews on this fragment
        // They are in the following regions:

        // Initialize view model
        binderViewModel = ViewModelProviders.of(this).get(MyBinderViewModel::class.java)

        // Initialize lists
        recentCards = emptyList<FullPokeCard>()
        mostUsedCards = emptyList<FullPokeCard>()
        allCards = emptyList<FullPokeCard>()


        //region RecyclerView #1 - Recently added cards
        recentViewManager = LinearLayoutManager(view.context,
                LinearLayoutManager.HORIZONTAL, false)
        recentViewAdapter = DisplayCardAdapter(view.context, recentCards) { index ->
            displayCardDetails(index, recentCards)
        }

        recentRecyclerView = view.findViewById<RecyclerView>(R.id.binder_recent_recyclerview).apply {

            setHasFixedSize(true)

            layoutManager = recentViewManager

            adapter = recentViewAdapter
        }

        // set onclicklistener so when the user clicks on this recyclerview, it will
        // bring them to the full view of these cards
        view.findViewById<ConstraintLayout>(R.id.binder_recent).setOnClickListener {
            val action = MyBinderHomeFragmentDirections.actionOpenBinder()
            action.listToDisplay = "RECENT"
            findNavController().navigate(action)
        }

        // used in recyclerviews to find max # of cards that can be displayed (less than 10)
        var maxIndex = 10

        // load in data from database
        binderViewModel.allCardsByDate.observe(this, Observer { cards ->
            cards?.let {
                if (it.size < maxIndex) {
                    maxIndex = it.size
                }
                recentCards = it.subList(0, maxIndex)
                recentViewAdapter.setResults(recentCards)
            }
        })
        //endregion

        //region RecyclerView #2 - Most Used Cards
        mostUsedViewManager = LinearLayoutManager(view.context,
                LinearLayoutManager.HORIZONTAL, false)
        mostUsedViewAdapter = DisplayCardAdapter(view.context, mostUsedCards) { index ->
            displayCardDetails(index, mostUsedCards)
        }

        mostUsedRecyclerView = view.findViewById<RecyclerView>(R.id.binder_most_used_recyclerview).apply {

            setHasFixedSize(true)

            layoutManager = mostUsedViewManager

            adapter = mostUsedViewAdapter
        }

        // set onclicklistener so when the user clicks on this recyclerview, it will
        // bring them to the full view of these cards
        view.findViewById<ConstraintLayout>(R.id.binder_most_used).setOnClickListener {
            val action = MyBinderHomeFragmentDirections.actionOpenBinder()
            action.listToDisplay = "MOST_USED"
            findNavController().navigate(action)
        }

        //endregion

        //region RecyclerView #3 - All Cards
        // load in data from database

        allViewManager = LinearLayoutManager(view.context,
                LinearLayoutManager.HORIZONTAL, false)
        allViewAdapter = DisplayCardAdapter(view.context, allCards) { index ->
            displayCardDetails(index, allCards)
        }

        allRecyclerView = view.findViewById<RecyclerView>(R.id.binder_all_recyclerview).apply {

            setHasFixedSize(true)

            layoutManager = allViewManager

            adapter = allViewAdapter
        }

        // set onclicklistener so when the user clicks on this recyclerview, it will
        // bring them to the full view of these cards
        view.findViewById<ConstraintLayout>(R.id.binder_all).setOnClickListener {
            val action = MyBinderHomeFragmentDirections.actionOpenBinder()
            action.listToDisplay = "ALL"
            findNavController().navigate(action)
        }

        maxIndex = 10
        binderViewModel.allCards.observe(this, Observer { cards ->
            cards?.let {
                if (it.size < maxIndex) {
                    maxIndex = it.size
                }
                allCards = it.subList(0, maxIndex)
                allViewAdapter.setResults(allCards)
            }
        })
        //endregion

    }

    fun displayCardDetails(index: Int, cardList: List<FullPokeCard>) {
        // when a card is selected, open detail fragment
        if (fragmentManager != null) {
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            val detail = CardDetailFragment()
            detail.setCard(cardList[index])
            fragmentTransaction.replace(R.id.inner_scroll, detail)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }



}