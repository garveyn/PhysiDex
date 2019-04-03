package com.physidex.physidex


import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable

class DeckAdapter(context: Context, val decks: ArrayList<Deck>) :
        RecyclerView.Adapter<DeckAdapter.DeckViewHolder>() {

    private val playString = context.getString(R.string.deck_play)
    private val cantPlayString = context.getString(R.string.deck_cannot_play)
    private val colorDragon = getColor(context, R.color.cardDragon)
    private val colorFire = getColor(context, R.color.cardFire)
    private val colorGrass = getColor(context, R.color.cardGrass)
    private val colorLightning = getColor(context, R.color.cardLightning)
    private val colorMetal = getColor(context, R.color.cardMetal)
    private val colorPsychic = getColor(context, R.color.cardPsychic)
    private val colorWater = getColor(context, R.color.cardWater)
    private val colorFairy = getColor(context, R.color.cardFairy)
    private val colorDark = getColor(context, R.color.cardDark)
    private val colorFighting = getColor(context, R.color.cardFighting)

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
        // TODO Set margins and padding

        holder.playDeckButton

        return holder

    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        var deck: Deck = decks[position]
        holder.nameField.text = deck.deckName
        holder.dateModified.text = deck.lastModifiedDate.toString()
        holder.view.isClickable = true
        holder.view.setOnClickListener { editDeck(deck) }

        if (deck.deckList.size > 0) {
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
        }

        if (deck.isReadyToPlay) {
            holder.playDeckButton.text = playString
            holder.playDeckButton.background = getDrawable(holder.context, R.drawable.ic_play_arrow)
            holder.playDeckButton.background.setTint(getColor(holder.context, R.color.play))
        } else {
            holder.playDeckButton.text = cantPlayString
            holder.playDeckButton.background = getDrawable(holder.context, R.drawable.ic_edit)
            holder.playDeckButton.background.setTint(getColor(holder.context, R.color.edit))
        }

    }

    private fun editDeck(deck: Deck) {
        TODO("Launch into deck editing view")
    }

    override fun getItemCount(): Int {
        return decks.size
    }
}