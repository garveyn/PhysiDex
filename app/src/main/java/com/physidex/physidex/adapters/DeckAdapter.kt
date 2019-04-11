package com.physidex.physidex.adapters


import android.content.Context
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import com.physidex.physidex.testClasses.Deck
import com.physidex.physidex.R
import com.physidex.physidex.database.entities.PokeDeckInfoEntity

class DeckAdapter :
        RecyclerView.Adapter<DeckAdapter.DeckViewHolder>() {

    var decks = emptyList<PokeDeckInfoEntity>()

    class DeckViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        var context:        Context     = view.context
        var nameField:      TextView    = view.findViewById(R.id.deck_name)
        var dateModified:   TextView    = view.findViewById(R.id.deck_last_modified)
        var playDeckButton: Button      = view.findViewById(R.id.deck_play)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {

        val deckView = LayoutInflater.from(parent.context)
                .inflate(R.layout.deck_manager_deck, parent, false)

        val holder = DeckViewHolder(deckView)

        // line inspired by:
        // https://stackoverflow.com/questions/5255184
        //     /android-and-setting-width-and-height-programmatically-in-dp-units
        val elevation = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f,
                holder.context.resources.displayMetrics)

        holder.view.elevation = elevation

        holder.playDeckButton

        return holder

    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        var deck: PokeDeckInfoEntity = decks[position]
        holder.nameField.text = deck.deckName
        holder.dateModified.text = deck.lastModified

        // Set click events
        holder.view.isClickable = true
        holder.view.isLongClickable = true
//        holder.view.setOnClickListener  { editDeck(deck) }
//        holder.view.setOnLongClickListener { markDeck(deck) }

//        if (deck.deckList.size > 0) {
            when (position % 10) // TODO change to be based on energy types
            {
                1 -> {
                    holder.view.setBackgroundColor(getColor(holder.context, R.color.cardDragon))
                }
                2 -> holder.view.setBackgroundColor(getColor(holder.context, R.color.cardDark))
                3 -> holder.view.setBackgroundColor(getColor(holder.context, R.color.cardFairy))
                4 -> holder.view.setBackgroundColor(getColor(holder.context, R.color.cardFighting))
                5 -> holder.view.setBackgroundColor(getColor(holder.context, R.color.cardFire))
                6 -> holder.view.setBackgroundColor(getColor(holder.context, R.color.cardGrass))
                7 -> holder.view.setBackgroundColor(getColor(holder.context, R.color.cardMetal))
                8 -> holder.view.setBackgroundColor(getColor(holder.context, R.color.cardWater))
                9 -> holder.view.setBackgroundColor(getColor(holder.context, R.color.cardPsychic))
                0 -> holder.view.setBackgroundColor(getColor(holder.context, R.color.cardLightning))
            }
//        }

        if (true) {
            holder.playDeckButton.text = holder.context.getString(R.string.deck_play)
            holder.playDeckButton.background = getDrawable(holder.context, R.drawable.ic_play_arrow)
            holder.playDeckButton.background.setTint(getColor(holder.context, R.color.play))
        } else {
            holder.playDeckButton.text = holder.context.getString(R.string.deck_cannot_play)
            holder.playDeckButton.background = getDrawable(holder.context, R.drawable.ic_edit)
            holder.playDeckButton.background.setTint(getColor(holder.context, R.color.edit))
        }

        holder.playDeckButton.height = holder.view.height

    }

    private fun editDeck(deck: Deck) {
        TODO("Launch into deck editing view")
    }

    private fun markDeck(deck: Deck) : Boolean {
        TODO("Mark for delete?")
    }

    override fun getItemCount(): Int {
        return decks.size
    }
}