package com.physidex.physidex

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class DeckAdapter(private val decks: ArrayList<Deck>) :
        RecyclerView.Adapter<DeckAdapter.DeckViewHolder>() {

    private val playString = "Play!" // TODO change to resource file
    private val cantPlayString = "Edit"

    class DeckViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        var nameField:      TextView    = view.findViewById(R.id.deck_name)
        var dateModified:   TextView    = view.findViewById(R.id.deck_last_modified)
        var playDeckButton: Button      = view.findViewById(R.id.deck_play)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {

        val deckView = LayoutInflater.from(parent.context)
                .inflate(R.layout.deck_manager_deck, parent, false)

        // TODO Set margins and padding

        return DeckViewHolder(deckView)

    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        var deck: Deck = decks[position]
        holder.nameField.text = deck.deckName
        holder.dateModified.text = deck.lastModifiedDate.toString()

        if (deck.isReadyToPlay) {
            holder.playDeckButton.text = playString
        } else {
            holder.playDeckButton.text = cantPlayString
        }

    }

    override fun getItemCount(): Int {
        return decks.size
    }
}