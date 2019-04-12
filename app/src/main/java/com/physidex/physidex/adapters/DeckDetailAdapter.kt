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

class DeckDetailAdapter(var context: Context)
    : RecyclerView.Adapter<DeckDetailAdapter.DeckDetailHolder>() {

    lateinit var recyclerView: RecyclerView
    val inflater: LayoutInflater = LayoutInflater.from(context)
    var cards = emptyList<FullPokeCard>()
    var expandedPosition: Int = RecyclerView.NO_POSITION
    var expandedHolder: DeckDetailHolder? = null

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

        // region Expand and contract viewholder if clicked
        /* code inspired by:
        * https://stackoverflow.com/questions/27203817/recyclerview-expand-collapse-items?answertab=oldest#tab-top
        */


        // Expand current box if clicked
        holder.itemView.isActivated = isExpanded
        holder.itemView.layoutParams.height =
                holder.itemView.context.resources.getDimension(R.dimen.pokecard_container_retracted).toInt()

        holder.itemView.setOnClickListener {

            // Retract previously expanded box
            if (expandedHolder != null && expandedPosition != RecyclerView.NO_POSITION) {
                expandedHolder!!.itemView.layoutParams.height =
                        expandedHolder!!.itemView.context.resources.getDimension(R.dimen.pokecard_container_retracted).toInt()
                expandedHolder!!.itemView.elevation =
                        expandedHolder!!.itemView.context.resources.getDimension(R.dimen.margin_8dp)

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

    }

    override fun onAttachedToRecyclerView(rv: RecyclerView) {
        super.onAttachedToRecyclerView(rv)

        recyclerView = rv
    }
}