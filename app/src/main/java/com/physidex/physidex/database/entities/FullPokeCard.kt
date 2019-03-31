package com.physidex.physidex.database.entities

import androidx.room.*
import com.physidex.physidex.GenericCard

class FullPokeCard {

//    override var cardName: String
//        get() = pokeCard!!.cardName
//        set(value) {
//            pokeCard!!.cardName = value
//        }

    @Embedded
    var pokeCard: PokeCardEntity? = null

    @Relation(parentColumn = "id",
              entityColumn = "cardId")
    var attacks: List<PokeAttackEntity> = ArrayList()
}