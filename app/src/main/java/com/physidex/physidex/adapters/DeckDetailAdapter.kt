package com.physidex.physidex.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.HeaderViewListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.entities.PokeDeckInfoEntity
import com.squareup.picasso.Picasso

class DeckDetailAdapter(var context: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var recyclerView: RecyclerView
    val inflater: LayoutInflater = LayoutInflater.from(context)
    var cards = emptyList<FullPokeCard>()
    var deckInfo = PokeDeckInfoEntity()
    var expandedPosition: Int = RecyclerView.NO_POSITION
    var expandedHolder: DeckDetailHolder? = null

    companion object {
        const val STANDARD: Int = 0
        const val HEADER: Int = 2
    }

    inner class DeckDetailHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardImage:          ImageView   = itemView.findViewById(R.id.card_image_view)
        val cardDuplicates:     TextView    = itemView.findViewById(R.id.card_owned)
        val cardAddButton:      Button      = itemView.findViewById(R.id.button_add)
        val cardSubtractButton: Button      = itemView.findViewById(R.id.button_add)
    }

    inner class DeckHeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val deckNameText:       TextView    = itemView.findViewById(R.id.deck_name)
        val deckSize:           TextView    = itemView.findViewById(R.id.deck_size)
        val creationDate:       TextView    = itemView.findViewById(R.id.creation_date)
        val modifyDate:         TextView    = itemView.findViewById(R.id.modified_date)
        val gamesPlayed:        TextView    = itemView.findViewById(R.id.games_played)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder: RecyclerView.ViewHolder
        val itemView: View
        if (viewType == STANDARD) {
            itemView = inflater.inflate(R.layout.deck_info, parent, false)
            holder = DeckHeaderHolder(itemView)
        } else {
            itemView = inflater.inflate(R.layout.deck_manager_card, parent, false)
            holder = DeckDetailHolder(itemView)
            holder.itemView.isClickable = true
        }

        return holder
    }

    /**
     * gets number of items that can be displayed
     *
     * @return The size of the card array plus 2 (to account for the header and footer)
     */
    override fun getItemCount() = cards.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DeckHeaderHolder) {
            holder.apply {
                deckNameText.text   = deckInfo.deckName
                creationDate.text   = deckInfo.created
                modifyDate.text     = deckInfo.lastModified
                deckSize.text       = String.format(
                        itemView.resources.getString(R.string.deck_cards_slash_total),
                        deckInfo.requiredSize,
                        deckInfo.requiredSize
                )
                gamesPlayed.text    = String.format(
                        itemView.resources.getString(R.string.deck_games_played),
                        deckInfo.gamesPlayed
                )
            }
        } else if (holder is DeckDetailHolder) {
            val currentCard = cards[position]
            val isExpanded: Boolean = position == expandedPosition

            // region Expand and contract viewholder if clicked
            /* code inspired by:
                * https://stackoverflow.com/questions/27203817/recyclerview-expand-collapse-items?answertab=oldest#tab-top
                */

            // Expand current box if clicked
            holder.apply {
                itemView.isActivated = isExpanded
                itemView.layoutParams.height =
                        itemView.context.resources.getDimension(R.dimen.pokecard_container_retracted).toInt()
                cardAddButton.visibility = Button.VISIBLE
                cardSubtractButton.visibility = Button.VISIBLE
            }

            // Retract previously expanded box
            holder.itemView.setOnClickListener {
                if (expandedHolder != null && expandedPosition != RecyclerView.NO_POSITION) {

                    expandedHolder!!.apply {
                        // Reset size
                        itemView.layoutParams.height = itemView.context.resources.getDimension(R.dimen.pokecard_container_retracted).toInt()
                        itemView.elevation = itemView.context.resources.getDimension(R.dimen.margin_8dp)

                        // Hide buttons
                        cardAddButton.visibility = Button.INVISIBLE
                        cardSubtractButton.visibility = Button.INVISIBLE
                    }

                    notifyItemChanged(expandedPosition)
                }

                expandedPosition = if (isExpanded) RecyclerView.NO_POSITION else position
                expandedHolder = if (isExpanded) null else holder
                notifyItemChanged(position)

            }

            // endregion


            holder.cardDuplicates.text = String.format(context.getString(R.string.binder_owned),
                    currentCard.pokeCard.numCopies)


            Picasso.with(context)
                    .load(currentCard.pokeCard.imageUrl)
                    .into(holder.cardImage)
        } else {
            throw IllegalArgumentException("$holder must be either DeckDetailHolder or DeckHeaderHolder")
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER
            else -> STANDARD
        }
    }

    override fun onAttachedToRecyclerView(rv: RecyclerView) {
        super.onAttachedToRecyclerView(rv)

        recyclerView = rv
    }
}