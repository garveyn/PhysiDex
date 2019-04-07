package com.physidex.physidex

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.database.entities.FullPokeCard
import com.squareup.picasso.Picasso

//TODO: rename to displayCard, lmao
class DisplaySearchAdapter(var context: Context, val itemClick: (Int) -> Unit) :
    RecyclerView.Adapter<DisplaySearchAdapter.DisplaySearchViewHolder> () {


    val inflater: LayoutInflater = LayoutInflater.from(context)
    var cards = emptyList<FullPokeCard>()

    constructor(context: Context,  cardList: MutableList<FullPokeCard>,
                itemClick: (Int) -> Unit) : this(context, itemClick) {
        setResults(cardList)
    }

    inner class DisplaySearchViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val cardImageView: ImageView = itemView.findViewById(R.id.card_image_view)
        val cardOwnedTextView: TextView = itemView.findViewById(R.id.card_owned)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val context = v.context
            //itemClick()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplaySearchViewHolder {
        val itemView = inflater.inflate(R.layout.my_binder_previews, parent, false)
        return DisplaySearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DisplaySearchViewHolder, position: Int) {
        val currentCard = cards[position]
        holder.itemView.isClickable = true
        holder.itemView.setOnClickListener{ itemClick(position) }

        holder.cardOwnedTextView.text = String.format(context.getString(R.string.binder_owned),
                currentCard.pokeCard!!.numCopies)


        Picasso.with(context)
                .load(currentCard.pokeCard?.imageUrl)
                .into(holder.cardImageView)
    }

    override fun getItemCount() = cards.size

    fun setResults(cardsReturned: List<FullPokeCard>) {
        this.cards = cardsReturned
        notifyDataSetChanged()
    }

}