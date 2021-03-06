package com.physidex.physidex.adapters

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.R
import com.physidex.physidex.database.daos.CardDao
import com.physidex.physidex.database.daos.DeckDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.entities.PokeDeckInfoEntity
import com.physidex.physidex.dimBehind
import com.physidex.physidex.pages.DeckDetailActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.delete_deck_prompt.*

/**
 * [RecyclerView.Adapter] implementation for the [RecyclerView] in [DeckDetailActivity]
 */
class DeckDetailAdapter(var context: Context, var fragment: DeckDetailActivity)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var recyclerView: RecyclerView
    val inflater: LayoutInflater = LayoutInflater.from(context)
    var cards = emptyList<FullPokeCard>()
    var copiesPerCard: Map<String, Int> = emptyMap()
    var totalCards: Int = 0
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
        val deleteWholeDeck:    Button      = itemView.findViewById(R.id.delete_deck)
        val addCardsToDeck:     Button      = itemView.findViewById(R.id.add_cards)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder: RecyclerView.ViewHolder
        val itemView: View
        if (viewType == HEADER) {
            itemView = inflater.inflate(R.layout.deck_info, parent, false)
            holder = DeckHeaderHolder(itemView)
        } else {
            itemView = inflater.inflate(R.layout.deck_detail_card, parent, false)
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
        if (holder is DeckHeaderHolder && getItemViewType(position) == HEADER) {
            holder.apply {
                deckNameText.text   = deckInfo.deckName
                creationDate.text   = deckInfo.created
                modifyDate.text     = deckInfo.lastModified
                deckSize.text       = String.format(
                        itemView.resources.getString(R.string.deck_cards_slash_total),
                        totalCards,
                        deckInfo.requiredSize
                )
                gamesPlayed.text    = String.format(
                        itemView.resources.getString(R.string.deck_games_played),
                        deckInfo.gamesPlayed
                )

                // Setup deck management buttons
                deleteWholeDeck.setOnClickListener { fragment.deleteDeck() }
                addCardsToDeck.setOnClickListener { fragment.addCards() }
            }
        } else if (holder is DeckDetailHolder && position > 0) {
            val currentCard = cards[position - 1]
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
                    copiesPerCard[currentCard.pokeCard.id])


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

    fun setCopies(copies: List<CardDao.CopiesPerId>) {
        copiesPerCard = copies.map { it.id to it.numCopies }.toMap()
        // also needs to update buttons for each card:
        // if the number of copies in this deck = the number of copies owned,
        // disable the "add" button, because no more copies of that card can be added.
        // this could possibly be done in the "add" (onclick) function instead.
    }


}