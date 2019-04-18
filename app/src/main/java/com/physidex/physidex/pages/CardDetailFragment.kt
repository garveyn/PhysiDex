package com.physidex.physidex.pages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.physidex.physidex.R
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.viewmodels.SearchViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_detail.*


class CardDetailFragment : Fragment() {

    lateinit var detailedCard: FullPokeCard
    private lateinit var cardViewModel: SearchViewModel
    private lateinit var tableLayout: TableLayout


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.card_detail, container, false)

        // Set up view model (used if added to binder)
        cardViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
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
                // Log.d("ALL_CARD_IDS", "allCardIds has updated. numCopies = ${detailedCard.pokeCard.numCopies}")
            }
        })

        // add to binder button
        val addButton: Button = view.findViewById(R.id.add_button)
        addButton.setOnClickListener {
            val previousNum: Int = detailedCard.pokeCard.numCopies
            detailedCard.pokeCard.numCopies++
            fillTable(detailedCard, detailedCard.getInfo())
            cardViewModel.insert(detailedCard, previousNum)
        }

        // remove from binder button
        val removeButton: Button = view.findViewById(R.id.remove_one_button)
        removeButton.setOnClickListener {
            if (detailedCard.pokeCard.numCopies > 1) {
                cardViewModel.removeOne(detailedCard)
            }
        }

        // remove from binder button
        val removeAllButton: Button = view.findViewById(R.id.remove_all_button)
        removeAllButton.setOnClickListener {
            if (detailedCard.pokeCard.numCopies > 0) {
                cardViewModel.removeAll(detailedCard)
            } else {
                fillTable(detailedCard, detailedCard.getInfo())
                updateButtons()
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
        }

        return view
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

}