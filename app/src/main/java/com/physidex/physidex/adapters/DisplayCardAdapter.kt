package com.physidex.physidex.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.database.daos.CardDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.squareup.picasso.Picasso


class DisplayCardAdapter(var context: Context, val itemClick: (Int) -> Unit) :
    RecyclerView.Adapter<DisplayCardAdapter.DisplaySearchViewHolder> () {

    val inflater: LayoutInflater = LayoutInflater.from(context)
    var cards = emptyList<FullPokeCard>()

    constructor(context: Context,  cardList: List<FullPokeCard>,
                itemClick: (Int) -> Unit) : this(context, itemClick) {
        setResults(cardList)
    }

    inner class DisplaySearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val cardImageView: ImageView = itemView.findViewById(R.id.card_image_view)
        val cardOwnedTextView: TextView = itemView.findViewById(R.id.card_owned)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplaySearchViewHolder {
        val itemView = inflater.inflate(R.layout.my_binder_previews, parent, false)
        itemView.elevation = context.resources.getDimension(R.dimen.elevation_3dp)
        return DisplaySearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DisplaySearchViewHolder, position: Int) {
        val currentCard = cards[position]
        holder.itemView.isClickable = true
        holder.itemView.setOnClickListener{ itemClick(position) }

        if (currentCard.numCopiesPerDeck != null) {
            holder.cardOwnedTextView.text = String.format(context.getString(R.string.deck_owned),
                    currentCard.numCopiesPerDeck, currentCard.pokeCard.numCopies)
        } else {
            holder.cardOwnedTextView.text = String.format(context.getString(R.string.binder_owned),
                    currentCard.pokeCard.numCopies)
        }

        Picasso.with(context)
                .load(currentCard.pokeCard.imageUrl)
                .into(holder.cardImageView)
    }

    override fun getItemCount() = cards.size

    fun setResults(cardsReturned: List<FullPokeCard>) {
        this.cards = cardsReturned
        notifyDataSetChanged()
    }

    /**
     * Set the number of copies for each card in one deck
     * @param allCards a list of all the ids of cards in my binder or one specific deck,
     * with the number of copies of each card that are in my binder / that deck.
     *
     */
    fun updateResults(allCards: List<CardDao.CopiesPerId>, isDeck: Boolean = false) {
        val cardsOwned: Map<String, Int> = allCards.map { it.id to it.numCopies }.toMap()
        for (card in this.cards) {
            if (cardsOwned.contains(card.pokeCard.id) ) {
                if (!isDeck) {
                    card.pokeCard.numCopies = cardsOwned[card.pokeCard.id] as Int
                } else {
                    card.numCopiesPerDeck = cardsOwned[card.pokeCard.id] as Int
                }
            } else {
                if (!isDeck) {
                    card.pokeCard.numCopies = 0
                } else {
                    card.numCopiesPerDeck = 0
                }
            }
        }
        notifyDataSetChanged()
    }

}