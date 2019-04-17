package com.physidex.physidex.pages

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.adapters.DeckDetailAdapter
import com.physidex.physidex.database.viewmodels.DeckDetailViewModel
import com.physidex.physidex.testClasses.TestData

class DeckDetailActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: DeckDetailAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: DeckDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deck_manager_editing)

        //val testData = TestData.buildDecks()

        viewManager = LinearLayoutManager(this)
        viewAdapter = DeckDetailAdapter(this)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {

            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }

        val deckId = intent.getIntExtra(DISPLAY_DECK, -1)
        if (deckId == -1) {
            Log.d("DECK_ERROR", "Deck id not passed to DeckDetailActivity")
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory {
            DeckDetailViewModel(this.application, deckId) }).get(DeckDetailViewModel::class.java)
        viewModel.deckInfo.observe(this, Observer { deck ->
            deck.let {
                viewAdapter.deckInfo = deck
                viewAdapter.notifyDataSetChanged()
            }

        })
        viewModel.deckCards.observe(this, Observer {cardsInDeck ->
            cardsInDeck?.let { viewAdapter.cards = cardsInDeck }
        })
        viewModel.deckCardCopies.observe(this, Observer { cardCopies ->
            cardCopies?.let { viewAdapter.setCopies(cardCopies) }
        })

    }

    // function from http://www.albertgao.xyz/2018/04/13/how-to-add-additional-parameters-to-viewmodel-via-kotlin/
    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
            }
}