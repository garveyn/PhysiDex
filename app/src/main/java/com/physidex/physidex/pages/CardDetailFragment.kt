package com.physidex.physidex.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.physidex.physidex.R
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.viewmodels.CardDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_detail.*


class CardDetailFragment : Fragment() {

    lateinit var detailedCard: FullPokeCard
    private lateinit var cardViewModel: CardDetailViewModel
    private lateinit var tableLayout: TableLayout
    var deckId: Int = -1

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.card_detail, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up view model (used if added to binder)
        cardViewModel = ViewModelProviders.of(this).get(CardDetailViewModel::class.java)
        var found = false
        cardViewModel.allCardIds.observe(this, Observer { copies ->
            copies?.let {

                copies.forEach{
                    if (it.id == detailedCard.pokeCard.id) {
                        found = true
                        detailedCard.pokeCard.numCopies = it.numCopies
                    }
                }
                if (!found) {
                    detailedCard.pokeCard.numCopies = 0
                }
                fillTable(detailedCard, detailedCard.getInfo())
                updateButtons()
            }
        })

        // add to binder button
        val addButton: Button = add_binder_button
        addButton.setOnClickListener {
            val previousNum: Int = detailedCard.pokeCard.numCopies
            detailedCard.pokeCard.numCopies++
            fillTable(detailedCard, detailedCard.getInfo())
            cardViewModel.insert(detailedCard, previousNum)
        }

        // remove from binder button
        val removeButton: Button = remove_one_button
        removeButton.setOnClickListener {
            if (detailedCard.pokeCard.numCopies > 1) {
                cardViewModel.removeOne(detailedCard)
            }
        }

        // remove from binder button
        val removeAllButton: Button = remove_all_button
        removeAllButton.setOnClickListener {
            if (detailedCard.pokeCard.numCopies > 0) {
                cardViewModel.removeAll(detailedCard)
            } else {
                fillTable(detailedCard, detailedCard.getInfo())
                updateButtons()
            }
        }

        // remove from deck button
        val removeDeckButton: Button = remove_deck_button
        removeDeckButton.setOnClickListener {
            if (detailedCard.numCopiesPerDeck != null &&
                    detailedCard.numCopiesPerDeck!! > 0 && deckId != -1) {
                detailedCard.numCopiesPerDeck = detailedCard.numCopiesPerDeck!! - 1
                fillTable(detailedCard, detailedCard.getInfo())
                cardViewModel.removeFromDeck(deckId, detailedCard)
                updateDeckButtons()
            }
        }

        // add to deck button
        val addDeckButton: Button = add_deck_button
        addDeckButton.setOnClickListener {
            if (detailedCard.numCopiesPerDeck != null && deckId != -1 &&
                    detailedCard.numCopiesPerDeck!! < detailedCard.pokeCard.numCopies) {
                detailedCard.numCopiesPerDeck = detailedCard.numCopiesPerDeck!! + 1
                fillTable(detailedCard, detailedCard.getInfo())
                cardViewModel.addToDeck(deckId, detailedCard)

                updateDeckButtons()
            }
        }

        // Set info
        if (this::detailedCard.isInitialized) {
            val cardDetailImage = view.findViewById<ImageView>(R.id.card_detail_image)
            Picasso.with(context)
                    .load(detailedCard.pokeCard.imageUrlHiRes)
                    .into(cardDetailImage)

            val info: Map<String, String> = detailedCard.getInfo()

            // Set background color
            if (context != null) {
                view.setBackgroundColor(detailedCard.getColor(context!!))
            }

            // Load TableLayout
            tableLayout = view.findViewById(R.id.detail_table)

            this.fillTable(detailedCard, info)

            if (detailedCard.numCopiesPerDeck != null) {
                removeAllButton.visibility = View.GONE
                removeButton.visibility = View.GONE
                addButton.visibility = View.GONE
                addDeckButton.visibility = View.VISIBLE
                removeDeckButton.visibility = View.VISIBLE
            }
        }
    }

    fun setCard(cardSelected: FullPokeCard) {
        detailedCard = cardSelected
    }

    fun fillTable(cardSelected: FullPokeCard, info: Map<String, String>) {

        tableLayout.removeAllViews()
        var fieldLayout: View
        var nameField: TextView
        var dataField: TextView

        for ((key, value) in info) {
            fieldLayout = this.layoutInflater.inflate(R.layout.card_detail_field, null)
            nameField = fieldLayout.findViewById(R.id.field_name)
            dataField = fieldLayout.findViewById(R.id.field_data)

            nameField.text = key
            dataField.text = value

            tableLayout.addView(fieldLayout)
        }
    }

    fun updateButtons() {
        val copies = detailedCard.pokeCard.numCopies
        when  {
            copies == 0 -> {
                remove_one_button.isEnabled = false
                remove_all_button.isEnabled = false
            }
            copies == 1 -> {
                remove_one_button.isEnabled = false
                remove_all_button.isEnabled = true
            }
            copies >= 1 -> {
                remove_one_button.isEnabled = true
                remove_all_button.isEnabled = true
            }
        }
    }

    fun updateDeckButtons() {
        val copies: Int = detailedCard.numCopiesPerDeck as Int
        when {
            copies < 1 -> {
                remove_deck_button.isEnabled = false
                add_deck_button.isEnabled = true
            }
            copies == detailedCard.pokeCard.numCopies -> {
                remove_deck_button.isEnabled = true
                add_deck_button.isEnabled = false
            }
            copies > 0 -> {
                remove_deck_button.isEnabled = true
                add_deck_button.isEnabled = true
            }
        }
    }

}