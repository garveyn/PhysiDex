package com.physidex.physidex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.pokemontcg.Pokemon
import android.os.AsyncTask
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.database.entities.FullPokeCard
import io.pokemontcg.model.Card
import kotlinx.android.synthetic.main.activity_display_card.*

class DisplaySearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DisplaySearchAdapter
    val fullPokeCards: MutableList<FullPokeCard> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_card)

        // Get the Intent that started this activity and extract the string
        val card = intent.getStringExtra(DISPLAY_CARD)

        // Set up RecyclerView
        recyclerView = searchResultView
        adapter = DisplaySearchAdapter(this) { index ->
            // when a card is selected, open detail fragment
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            var detail = CardDetailFragment()
            detail.setCard(fullPokeCards[index])
            fragmentTransaction.replace(R.id.display_cards, detail)
            fragmentTransaction.commit()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        //recyclerView.addItemDecoration(GridItemDecoration(1, 2))

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
                Log.d("FIRST CARD", cardsReturned[0].toString())
                return cardsReturned
            } else {
                Log.d("FIRST CARD", "No cards were returned")
            }

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

                for (card in result) {
                    fullPokeCards.add(FullPokeCard(card))
                }
                searchResultView.visibility = View.VISIBLE
                adapter.setResults(fullPokeCards)

            } else {
               resultText.text = getString(R.string.no_cards_found)
            }
        }
    }
}