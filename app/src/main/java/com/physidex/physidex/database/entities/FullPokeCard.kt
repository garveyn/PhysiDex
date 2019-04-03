package com.physidex.physidex.database.entities

import androidx.room.*
import com.physidex.physidex.GenericCard
import io.pokemontcg.model.Attack
import io.pokemontcg.model.Card

class FullPokeCard {

//    override var cardName: String
//        get() = pokeCard!!.cardName
//        set(value) {
//            pokeCard!!.cardName = value
//        }

    constructor(card: Card) {
        val cardEntity: PokeCardEntity = PokeCardEntity(card)
        this.pokeCard = cardEntity
        if (card.attacks != null) {
            //val  = card.attacks as List<Attack>
            for (attack in card.attacks as List<Attack>) {
                this.attacks.add(PokeAttackEntity(attack, cardEntity.id))
            }
        }

    }

    @Embedded
    var pokeCard: PokeCardEntity? = null

    @Relation(parentColumn = "id",
              entityColumn = "cardId")
    var attacks: MutableList<PokeAttackEntity> = ArrayList()
}