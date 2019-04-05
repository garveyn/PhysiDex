package com.physidex.physidex

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.physidex.physidex.database.entities.FullPokeCard
import com.squareup.picasso.Picasso

//TODO: rename to displayCard, lmao
class DisplaySearchAdapter(var context: Context, val itemClick: (Int) -> Unit) :
    RecyclerView.Adapter<DisplaySearchAdapter.DisplaySearchViewHolder> () {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cards = emptyList<FullPokeCard>()

    inner class DisplaySearchViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val cardView: ImageView = itemView.findViewById(R.id.cardSearchResult)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val context = v.context
            //itemClick()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplaySearchViewHolder {
        val itemView = inflater.inflate(R.layout.search_result_item, parent, false)
        return DisplaySearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DisplaySearchViewHolder, position: Int) {
        val currentCard = cards[position]
        holder.itemView.isClickable = true
        holder.itemView.setOnClickListener{ itemClick(position) }
        Picasso.with(context)
                .load(currentCard.pokeCard?.imageUrl)
                .into(holder.cardView)
    }

    override fun getItemCount() = cards.size

    fun setResults(cardsReturned: List<FullPokeCard>) {
        this.cards = cardsReturned
        notifyDataSetChanged()
    }

}