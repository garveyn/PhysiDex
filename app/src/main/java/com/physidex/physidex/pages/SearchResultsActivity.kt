package com.physidex.physidex.pages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.pokemontcg.Pokemon
import android.os.AsyncTask
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.adapters.DisplayCardAdapter
import com.physidex.physidex.database.daos.CardDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.viewmodels.SearchViewModel
import com.physidex.physidex.testClasses.TestData
import io.pokemontcg.model.Card
import kotlinx.android.synthetic.main.activity_display_card.*

class SearchResultsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DisplayCardAdapter
    private lateinit var cardViewModel: SearchViewModel
    var fullPokeCards: List<FullPokeCard> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_card)

        // Create Action bar
        val toolbar: Toolbar = findViewById(R.id.search_toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        toolbar.title = getString(R.string.search_results)

        // Get the Intent that started this activity and extract the string
        val card = intent.getStringExtra(DISPLAY_CARD)

        // Set up RecyclerView
        recyclerView = searchResultView
        adapter = DisplayCardAdapter(this) { index ->
            // when a card is selected, open detail fragment
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val detail = CardDetailFragment()
            detail.detailedCard = fullPokeCards[index]
            fragmentTransaction.replace(R.id.display_cards, detail)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        //recyclerView.addItemDecoration(GridItemDecoration(1, 2))

        // Set up view model (used to check numCopies of each card)
        cardViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        cardViewModel.allCardIds.observe(this, Observer { cards ->
            cards?.let { adapter.updateResults(it)}
        })

        // Capture the layout's TextView and set the string as its text
        cardSearch(card)
    }

    private fun cardSearch(pokemonName: String) {

        searchInput.text = String.format(getString(R.string.search_input), pokemonName)

        if (pokemonName.isNotEmpty()) {

            PokemonQueryTask().execute(pokemonName)
        }
    }

    inner class PokemonQueryTask : AsyncTask<String, Int, List<Card>>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
            searchResultView.visibility = View.INVISIBLE
        }

        override fun doInBackground(vararg params: String): List<Card>? {
            val pokemon = Pokemon()
            val cardsReturned = pokemon.card()
                    .where {
                        name = params[0]
                    }.all()

            if (cardsReturned.isNotEmpty()) {
                //Log.d("FIRST CARD", cardsReturned[0].toString())
                return cardsReturned
            }
//            } else {
//                Log.d("FIRST CARD", "No cards were returned")
//            }

            return null
        }

        override fun onPostExecute(result: List<Card>?) {
            // TODO: check if cards are already owned
            super.onPostExecute(result)

            progressBar.visibility = View.INVISIBLE
            if (result != null && result.isNotEmpty()) {
                Log.d("OnPostExecute", "Cards received.")
                resultText.text = String.format(getString(R.string.results_number), result.size)
                TestData.loadCards(result)
                val loadedCards = mutableListOf<FullPokeCard>()

                for (card in result) {
                    loadedCards.add(FullPokeCard(card))
                }
                fullPokeCards = loadedCards
                searchResultView.visibility = View.VISIBLE
                adapter.setResults(fullPokeCards)

                var existingCards: List<CardDao.CopiesPerId> = emptyList()
                if (cardViewModel.allCardIds.value != null) {
                    existingCards = cardViewModel.allCardIds.value as List<CardDao.CopiesPerId>
                    adapter.updateResults(existingCards)
                }

            } else {
               resultText.text = getString(R.string.no_cards_found)
            }
        }
    }
}