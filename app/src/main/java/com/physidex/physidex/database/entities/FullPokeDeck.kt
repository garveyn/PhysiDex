package com.physidex.physidex.database.entities

import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation

class FullPokeDeck(pokeDeck: PokeDeckInfoEntity) {

    @Embedded
    var deckInfo: PokeDeckInfoEntity = pokeDeck

    var cards: MutableList<FullPokeCard> = mutableListOf()

//    fun getDeckSize(): Int {
//        for (card in )
//    }

//    val isReadyToPlay: Boolean
//        get() = (deckSize == deckInfo.requiredSize)
}