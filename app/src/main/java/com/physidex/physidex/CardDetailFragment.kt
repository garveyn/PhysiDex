package com.physidex.physidex

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.physidex.physidex.database.entities.FullPokeCard
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_detail.*

class CardDetailFragment : Fragment() {

    private lateinit var card: FullPokeCard

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.card_detail, container, false)

        // Set info
        if (this::card.isInitialized) {
            val cardDetailImage = view.findViewById<ImageView>(R.id.card_detail_image)
            Picasso.with(context)
                    .load(card.pokeCard?.imageUrlHiRes)
                    .into(cardDetailImage)

            this.fillTable(card)
        }

        return view
    }

    fun setCard(cardSelected: FullPokeCard) {
        card = cardSelected
    }

    fun fillTable(cardSelected: FullPokeCard) {

        val info: Map<String, String> = cardSelected.getInfo()
        //TODO: Fill table with info in map. 
    }

}