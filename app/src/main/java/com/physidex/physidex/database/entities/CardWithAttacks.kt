package com.physidex.physidex.database.entities

import androidx.room.*

class CardWithAttacks {

    @Embedded
    var pokeCard: PokeCard? = null

    @Relation(parentColumn = "id",
              entityColumn = "cardId")
    var attacks: List<PokeAttack> = ArrayList()
}