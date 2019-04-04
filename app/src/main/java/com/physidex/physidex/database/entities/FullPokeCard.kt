package com.physidex.physidex.database.entities

import android.util.Log
import androidx.room.*
import com.physidex.physidex.GenericCard
import io.pokemontcg.model.Attack
import io.pokemontcg.model.Card

class FullPokeCard {

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

    fun getInfo(){
        if (this.pokeCard != null ) {

            var info: MutableMap<String, String> = mutableMapOf()
            // TODO: get a Map with all members that are not null, to put into table
            Log.d("CARD_PRINT", this.pokeCard.toString())
            this.pokeCard!!::class.members.forEach { member ->
                Log.d("CARD_TEST", member.name)
                if (member != null) {

                }
            }
        }
    }
}