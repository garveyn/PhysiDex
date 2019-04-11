package com.physidex.physidex.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.physidex.physidex.R
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.viewmodels.SearchViewModel
import com.squareup.picasso.Picasso


class CardDetailFragment : Fragment(), View.OnClickListener {

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

        // add to binder
        val button: Button = view.findViewById(R.id.add_button)
        button.setOnClickListener(this)

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

        //TODO: Fill table with info in map.
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

    override fun onClick(view: View) {

        cardViewModel.insert(detailedCard, 1)
        //detailedCard.pokeCard.numCopies++
    }

}