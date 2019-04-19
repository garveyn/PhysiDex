package com.physidex.physidex.pages

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.adapters.DeckDetailAdapter
import com.physidex.physidex.database.viewmodels.DeckDetailViewModel
import com.physidex.physidex.dimBehind
import kotlinx.android.synthetic.main.deck_details.*
import kotlinx.android.synthetic.main.deck_info.*
import kotlinx.android.synthetic.main.delete_deck_prompt.*

class DeckDetailActivity : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: DeckDetailAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: DeckDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.deck_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewManager = LinearLayoutManager(requireContext())
        viewAdapter = DeckDetailAdapter(requireContext())

        recyclerView = dd_recyclerView.apply {

            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter
        }

        val safeArgs: DeckDetailActivityArgs by navArgs()
        val deckId = safeArgs.deckID
        if (deckId == -1) {
            Log.d("DECK_ERROR", "Deck id not passed to DeckDetailActivity")
        }

        // get data from view model
        viewModel = ViewModelProviders.of(this,
                (requireActivity() as MainActivity).viewModelFactory {
            DeckDetailViewModel(this.requireActivity().application, deckId) }).get(DeckDetailViewModel::class.java)

        // set data in RecyclerView
        viewModel.deckInfo.observe(this, Observer { deck ->
            deck?.let {
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
        viewModel.numCards.observe(this, Observer { num ->
            num?.let { viewAdapter.totalCards = num }
        })

        // set the numbers of each card
        viewModel.numPokemon.observe(this, Observer { num ->
            num?.let { deck_pokemon_value.text = num.toString() }
        })
        viewModel.numTrainers.observe(this, Observer { num ->
            num?.let { deck_trainer_value.text = num.toString() }
        })
        viewModel.numEnergy.observe(this, Observer { num ->
            num?.let { deck_energy_value.text = num.toString() }
        })

        // Setup deck management buttons
        delete_deck.setOnClickListener { deleteDeck() }
        add_cards.setOnClickListener { addCards() }


    }

    fun addCards() {
        val action = DeckDetailActivityDirections.actionAddCards()
        if (viewModel.deckInfo.value != null) {
            action.deckID = viewModel.deckInfo.value!!.id
            findNavController().navigate(action)

            // TODO change this to not temp
            val cardGrid = MyBinderGridFragment()
            cardGrid.setCopiesPerDeck(viewModel.deckCardCopies.value!!)
        }


    }

    fun deleteDeck() {
        // Bring up new deck popup
        val inflater = LayoutInflater.from(context)

        val popupView = inflater.inflate(R.layout.delete_deck_prompt, null)

        val deleteDeck = PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT, true).apply {
            showAtLocation(view, Gravity.CENTER, 0, 0)
            dimBehind()
        }

        delete.setOnClickListener {
            
        }

        // Do nothing if canceled
        cancel.setOnClickListener { deleteDeck.dismiss() }


    }
}