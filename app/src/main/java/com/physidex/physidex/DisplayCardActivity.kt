package com.physidex.physidex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import io.pokemontcg.Pokemon
import android.os.AsyncTask
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.database.entities.FullPokeCard
import com.squareup.picasso.Picasso
import io.pokemontcg.model.Card
import kotlinx.android.synthetic.main.activity_display_card.*

class DisplayCardActivity : AppCompatActivity() {

//    var cardImageView: ImageView = findViewById(R.id.cardImageView)
    //var response: String = ""
    //var responseView: TextView = findViewById(R.id.cardResponse)
    //private lateinit var mCardImageView: ImageView

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DisplaySearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_card)

        // Get the Intent that started this activity and extract the string
        val card = intent.getStringExtra(DISPLAY_CARD)

        // Set up RecyclerView
        recyclerView = searchResultView
        adapter = DisplaySearchAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Capture the layout's TextView and set the string as its text
        cardSearch(card)
    }

    private fun cardSearch(pokemonName: String) {

        cardResponse.text = pokemonName

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
            Log.d("PARAM", params[0])
            val cardsReturned = pokemon.card()
                    .where {
                        name = params[0]
                    }.all()

            if (cardsReturned.isNotEmpty()) {
                Log.d("FIRST CARD", cardsReturned[0].toString())
                // response = cards[0].toString()
                // cardsReturned = cards
                return cardsReturned
            } else {
                Log.d("FIRST CARD", "No cards were returned")
            }

            return null
        }

        override fun onPostExecute(result: List<Card>?) {
            // TODO: check if cards are already owned
            super.onPostExecute(result)
            Log.d("DOWNLOAD","Downloaded $result bytes")
            //cardImageView.setImageBitmap(result)

            progressBar.visibility = View.INVISIBLE
            if (result != null && result.isNotEmpty()) {
                Log.d("OnPostExecute", "Cards received.")
                ArrayOfDecks.loadCards(result)
                val fullPokeCards: MutableList<FullPokeCard> = mutableListOf()
                for (card in result) {
                    fullPokeCards.add(FullPokeCard(card))
                }
                searchResultView.visibility = View.VISIBLE
                adapter.setResults(fullPokeCards)
//                cardImageView.visibility = View.VISIBLE
//                Picasso.with(this@DisplayCardActivity)
//                        .load(result[0].imageUrl)
//                        .into(cardImageView)

            } else {
               cardResponse.text = getString(R.string.no_cards_found)
            }
        }
    }
}