package com.physidex.physidex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.database.entities.FullPokeCard

class MyBinderPage : Fragment() {

    private lateinit var recentCards:           MutableList<FullPokeCard>
    private lateinit var recentRecyclerView:    RecyclerView
    private lateinit var recentViewAdapter:     RecyclerView.Adapter<*>
    private lateinit var recentViewManager:     RecyclerView.LayoutManager

    private lateinit var mostUsedCards:         MutableList<FullPokeCard>
    private lateinit var mostUsedRecyclerView:  RecyclerView
    private lateinit var mostUsedViewAdapter:   RecyclerView.Adapter<*>
    private lateinit var mostUsedViewManager:   RecyclerView.LayoutManager

    private lateinit var allCards:              MutableList<FullPokeCard>
    private lateinit var allRecyclerView:       RecyclerView
    private lateinit var allViewAdapter:        RecyclerView.Adapter<*>
    private lateinit var allViewManager:        RecyclerView.LayoutManager

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.my_binder_main, container, false)

        // There are three different RecyclerViews on this fragment
        // They are in the following regions: TODO Initialize cards for all three

        //region TODO : Remove, only for testing

        val data = TestData.buildFullCards()

        recentCards = mutableListOf()
        mostUsedCards = mutableListOf()
        allCards = mutableListOf()

        loadingTestData@ for (card in data) {
            when
            {
                recentCards.size < 10 -> recentCards.add(card)
                mostUsedCards.size < 10 -> mostUsedCards.add(card)
                allCards.size < 10 -> allCards.add(card)
                else -> break@loadingTestData
            }
        }

        //endregion

        //region RecyclerView #1 - Recently added cards
        recentViewManager = LinearLayoutManager(view.context,
                LinearLayoutManager.HORIZONTAL, false)
        recentViewAdapter = DisplaySearchAdapter(view.context, recentCards) {index ->
            displayCardDetails(index, recentCards)
        }

        recentRecyclerView = view.findViewById<RecyclerView>(R.id.binder_recent_recyclerview).apply {

            setHasFixedSize(true)

            layoutManager = recentViewManager

            adapter = recentViewAdapter
        }
        //endregion

        //region RecyclerView #2 - Most Used Cards
        mostUsedViewManager = LinearLayoutManager(view.context,
                LinearLayoutManager.HORIZONTAL, false)
        mostUsedViewAdapter = DisplaySearchAdapter(view.context, mostUsedCards) {index ->
            displayCardDetails(index, mostUsedCards)
        }

        mostUsedRecyclerView = view.findViewById<RecyclerView>(R.id.binder_most_used_recyclerview).apply {

            setHasFixedSize(true)

            layoutManager = mostUsedViewManager

            adapter = mostUsedViewAdapter
        }
        //endregion

        //region RecyclerView #3 - All Cards
        allViewManager = LinearLayoutManager(view.context,
                LinearLayoutManager.HORIZONTAL, false)
        allViewAdapter = DisplaySearchAdapter(view.context, allCards) {index ->
            displayCardDetails(index, allCards)
        }

        allRecyclerView = view.findViewById<RecyclerView>(R.id.binder_all_recyclerview).apply {

            setHasFixedSize(true)

            layoutManager = allViewManager

            adapter = allViewAdapter
        }
        //endregion

        return view
    }

    fun displayCardDetails(index: Int, cardList: MutableList<FullPokeCard>) {
        // when a card is selected, open detail fragment
        if (fragmentManager != null) {
            val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            val detail = CardDetailFragment()
            detail.setCard(cardList[index])
            fragmentTransaction.replace(R.id.display_cards, detail)
            fragmentTransaction.commit()
        }
    }

}