package com.physidex.physidex.database.entities

import android.util.Log
import androidx.room.*
import com.physidex.physidex.GenericCard
import io.pokemontcg.model.Ability
import io.pokemontcg.model.Attack
import io.pokemontcg.model.Card
import kotlin.reflect.full.memberProperties

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

    // get a Map (key-value list) of all members that are not null
    fun getInfo(): Map<String, String> {
        val info: MutableMap<String, String> = mutableMapOf()
        if (this.pokeCard != null ) {
            val card: PokeCardEntity = pokeCard as PokeCardEntity

            // TODO: make toString methods in order to clean this up
            info["Card Name"] = card.cardName
            if (card.type1 != null && card.type2 != null) {
                info["Type"] = "$card.type1, $card.type2"
            } else if (card.type1 != null) { info["Type"] = "$card.type1" }
            if (card.evolvesFrom != null) { info["Evolves From"] = card.evolvesFrom as String }
            if (card.hp != null) { info["HP"] = card.hp.toString() }
            if (card.evolvesFrom != null) { info["Evolves From"] = card.evolvesFrom as String }
            if (card.ability != null) {
                val ability = card.ability as Ability
                var abilityText = "$ability.name"
                if (ability.type != null) { abilityText = "$abilityText $ability.type" }
                abilityText = "$abilityText $card.text"
                info["Ability"] = abilityText
            }
            if (!this.attacks.isEmpty()) {
                for ((index, attack) in this.attacks.withIndex()) {
                    info["Attack $index"] = attack.name
                    if (attack.text != null) { info["Description $index"] = attack.text as String }
                    info["Attack Cost $index"] = attack.cost
                    if (attack.damage != null) { info["Damage $index"] = attack.damage as String }
                    info["Converted Energy Cost $index"] = attack.convEnergyCost.toString()
                }
            }
            if (card.retreatCost != null) { info["Retreat Cost"] = card.retreatCost.toString() }
            if (card.weakness != null) {
                val weakness = card.weakness as PokeEffect
                info["Weakness"] = "$weakness.type $weakness.value"
            }
            if (card.resistance != null) {
                val resistance = card.resistance as PokeEffect
                info["Weakness"] = "$resistance.type $resistance.value"
            }
            if (card.cardText != null) { info["Card Text"] = card.cardText as String }
            if (card.rarity != null) { info["Rarity"] = card.rarity as String}
            info["Series"] = card.series
            info["Set"] = card.set
            info["Number of Copies in My Binder"] = card.numCopies.toString()

        }
        return info
    }
}