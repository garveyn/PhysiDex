package com.physidex.physidex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.database.entities.FullPokeCard
import com.squareup.picasso.Picasso

class DeckDetailAdapter(var context: Context) : RecyclerView.Adapter<DeckDetailAdapter.DeckDetailHolder>() {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    var cards = emptyList<FullPokeCard>()
    var expandedPosition: Int = -1 // Initialized to dummy value

    inner class DeckDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardImage:      ImageView   = itemView.findViewById(R.id.card_image_view)
        val cardDuplicates: TextView    = itemView.findViewById(R.id.card_owned)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckDetailHolder {
        val itemView = inflater.inflate(R.layout.my_binder_previews, parent, false)
        val holder = DeckDetailHolder(itemView)
        holder.itemView.isClickable = true

        return holder
    }

    override fun getItemCount() = cards.size

    override fun onBindViewHolder(holder: DeckDetailHolder, position: Int) {
        val currentCard = cards[position]
        val isExpanded: Boolean = position == expandedPosition

        holder.itemView.isActivated = isExpanded

        holder.cardDuplicates.text = String.format(context.getString(R.string.binder_owned),
                currentCard.pokeCard.numCopies)


        Picasso.with(context)
                .load(currentCard.pokeCard.imageUrl)
                .into(holder.cardImage)

    }
}