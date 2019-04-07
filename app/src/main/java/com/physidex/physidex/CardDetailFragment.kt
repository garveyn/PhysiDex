package com.physidex.physidex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.physidex.physidex.database.PokeCardViewModel
import com.physidex.physidex.database.entities.FullPokeCard
import com.squareup.picasso.Picasso


class CardDetailFragment : Fragment(), View.OnClickListener {

    lateinit var detailedCard: FullPokeCard
    private lateinit var cardViewModel: PokeCardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.card_detail, container, false)

        // Set up view model (used if added to binder)
        cardViewModel = ViewModelProviders.of(this).get(PokeCardViewModel::class.java)

        // add to binder
        val button: Button = view.findViewById(R.id.add_button)
        button.setOnClickListener(this)

        // Set info
        if (this::detailedCard.isInitialized) {
            val cardDetailImage = view.findViewById<ImageView>(R.id.card_detail_image)
            Picasso.with(context)
                    .load(detailedCard.pokeCard?.imageUrlHiRes)
                    .into(cardDetailImage)

            val info: Map<String, String> = detailedCard.getInfo()
            this.fillTable(detailedCard, info)
        }

        return view
    }

    fun setCard(cardSelected: FullPokeCard) {
        detailedCard = cardSelected
    }

    fun fillTable(cardSelected: FullPokeCard, info: Map<String, String>) {

        //TODO: Fill table with info in map.

        for ((key, value) in info) {

        }
    }

    override fun onClick(view: View) {
        if (detailedCard.pokeCard != null) {
            val previousNumCopies = detailedCard.pokeCard?.numCopies as Int
            detailedCard.pokeCard?.numCopies = previousNumCopies + 1
        }
        // cardViewModel.insert(detailedCard)
    }

}