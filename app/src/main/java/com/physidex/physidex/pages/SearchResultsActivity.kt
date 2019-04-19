package com.physidex.physidex.pages

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.pokemontcg.Pokemon
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.adapters.DisplayCardAdapter
import com.physidex.physidex.database.daos.CardDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.viewmodels.CardDetailViewModel
import com.physidex.physidex.testClasses.TestData
import io.pokemontcg.model.Card
import kotlinx.android.synthetic.main.activity_display_card.*

class SearchResultsActivity : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DisplayCardAdapter
    private lateinit var cardViewModel: CardDetailViewModel
    var fullPokeCards: List<FullPokeCard> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_display_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the Intent that started this activity and extract the string
        val safeArgs: SearchResultsActivityArgs by navArgs()
        val card = safeArgs.query

        // Set up RecyclerView
        recyclerView = searchResultView
        adapter = DisplayCardAdapter(requireContext()) { index ->
            // when a card is selected, open detail fragment
            val fragmentManager = requireFragmentManager()
            val fragmentTransaction = fragmentManager.beginTransaction()
            val detail = CardDetailFragment()
            detail.detailedCard = fullPokeCards[index]
            fragmentTransaction.replace(R.id.display_cards, detail)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        //recyclerView.addItemDecoration(GridItemDecoration(1, 2))

        // Set up view model (used to check numCopies of each card)
        cardViewModel = ViewModelProviders.of(this).get(CardDetailViewModel::class.java)
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