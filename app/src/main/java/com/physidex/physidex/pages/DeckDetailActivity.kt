package com.physidex.physidex.pages

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.adapters.DeckDetailAdapter
import com.physidex.physidex.database.viewmodels.DeckDetailViewModel
import kotlinx.android.synthetic.main.deck_details.*

class DeckDetailActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: DeckDetailAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: DeckDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deck_details)

        // Create Action bar
        val toolbar: Toolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            setBackgroundDrawable(ColorDrawable(
                    ContextCompat.getColor(this@DeckDetailActivity, R.color.colorPrimary)))

        }

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

        // get data from view model
        viewModel = ViewModelProviders.of(this, viewModelFactory {
            DeckDetailViewModel(this.application, deckId) }).get(DeckDetailViewModel::class.java)

        // set data in RecyclerView
        viewModel.deckInfo.observe(this, Observer { deck ->
            deck.let {
                viewAdapter.deckInfo = deck
                viewAdapter.notifyDataSetChanged()

                actionbar?.title = deck.deckName
            }

        })
        viewModel.deckCards.observe(this, Observer {cardsInDeck ->
            cardsInDeck?.let { viewAdapter.cards = cardsInDeck }
        })
        viewModel.deckCardCopies.observe(this, Observer { cardCopies ->
            cardCopies?.let { viewAdapter.setCopies(cardCopies) }
        })
        viewModel.numCards.observe(this, Observer { num ->
            num.let { viewAdapter.totalCards = num }
        })

        // set the numbers of each card
        viewModel.numPokemon.observe(this, Observer { num ->
            num.let { deck_pokemon_value.text = num.toString() }
        })
        viewModel.numTrainers.observe(this, Observer { num ->
            num.let { deck_trainer_value.text = num.toString() }
        })
        viewModel.numEnergy.observe(this, Observer { num ->
            num.let { deck_energy_value.text = num.toString() }
        })



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.deck_detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_card_action -> {
                // add some sweet cards
                return true
            }
            R.id.delete_deck_action -> {
                // delete the whole dang deck (and probably confirm with the user first
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // function from http://www.albertgao.xyz/2018/04/13/how-to-add-additional-parameters-to-viewmodel-via-kotlin/
    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
            }
}