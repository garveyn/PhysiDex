//package com.physidex.physidex
//
//import android.content.Context
//import android.view.LayoutInflater
//import androidx.recyclerview.widget.RecyclerView
//import com.physidex.physidex.database.entities.FullPokeCard
//
//class MyBinderAdapter(var context: Context, val itemClick: (Int) -> Unit) :
//    RecyclerView.Adapter<MyBinderAdapter.MyBinderViewHolder> () {
//
//    val inflater: LayoutInflater = LayoutInflater.from(context)
//    var cards = emptyList<FullPokeCard>()
//
//    constructor(context: Context,  cardList: MutableList<FullPokeCard>,
//                itemClick: (Int) -> Unit) : this(context, itemClick) {
//        setResults(cardList)
//    }
//}