package com.physidex.physidex.database.entities

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.room.*
import com.physidex.physidex.R
import io.pokemontcg.model.Ability
import io.pokemontcg.model.Attack
import io.pokemontcg.model.Card
import io.pokemontcg.model.Type

class FullPokeCard(pokeCard: PokeCardEntity) {

    @Ignore
    constructor(card: Card): this(PokeCardEntity(card, 0)) {
        if (card.attacks != null) {
            for (attack in card.attacks as List<Attack>) {
                this.attacks.add(PokeAttackEntity(attack, this.pokeCard.id))
            }
        }
    }

    @Embedded
    var pokeCard: PokeCardEntity = pokeCard

    @Relation(parentColumn = "id",
              entityColumn = "card_id")
    var attacks: MutableList<PokeAttackEntity> = ArrayList()

    @Ignore
    var numCopiesPerDeck: Int? = null

    @Ignore
    fun setNumCopiesPerDeck(copies: Int) {
        this.numCopiesPerDeck = copies
    }


    // TODO: add isPokemon, isTrainer, isBasicEnergy

    fun getColor(context: Context) : Int {
        if (pokeCard.type1 != null) {
            try {
                when (Type.valueOf(pokeCard.type1!!))
                {
                    Type.COLORLESS -> return ContextCompat.getColor(context, R.color.cardColorless)
                    Type.DARKNESS -> return ContextCompat.getColor(context, R.color.cardDark)
                    Type.DRAGON -> return ContextCompat.getColor(context, R.color.cardDragon)
                    Type.FAIRY -> return ContextCompat.getColor(context, R.color.cardFairy)
                    Type.FIGHTING -> return ContextCompat.getColor(context, R.color.cardFighting)
                    Type.FIRE -> return ContextCompat.getColor(context, R.color.cardFire)
                    Type.GRASS -> return ContextCompat.getColor(context, R.color.cardGrass)
                    Type.LIGHTNING -> return ContextCompat.getColor(context, R.color.cardLightning)
                    Type.METAL -> return ContextCompat.getColor(context, R.color.cardMetal)
                    Type.PSYCHIC -> return ContextCompat.getColor(context, R.color.cardPsychic)
                    Type.WATER -> return ContextCompat.getColor(context, R.color.cardWater)
                    Type.UNKNOWN -> return ContextCompat.getColor(context, R.color.cardColorless)
                }
            } catch(e: IllegalArgumentException) {
                Log.d("COLOR_ERROR", "${pokeCard.type1} is not a valid type!")
                return ContextCompat.getColor(context, R.color.cardColorless)
            }
        } else {
            return ContextCompat.getColor(context, R.color.cardColorless)
        }
    }

    // get a Map (key-value list) of all members that are not null
    fun getInfo(): Map<String, String> {
        val info: MutableMap<String, String> = mutableMapOf()
        val card: PokeCardEntity = pokeCard

        // TODO: make toString methods in order to clean this up
        info["Number of Copies in My Binder"] = card.numCopies.toString()
        info[""] = ""
        info["Card Name"] = card.cardName
        if (card.type1 != null && card.type2 != null) {
            info["Type"] = "${card.type1}, ${card.type2}"
        } else if (card.type1 != null) { info["Type"] = "${card.type1}" }
        if (card.evolvesFrom != null) { info["Evolves From"] = card.evolvesFrom as String }
        if (card.hp != null) { info["HP"] = card.hp.toString() }
        if (card.evolvesFrom != null) { info["Evolves From"] = card.evolvesFrom as String }
        if (card.ability != null) {
            val ability = card.ability as Ability
            var abilityText = ability.name
            if (ability.type != null) { abilityText = "$abilityText ${ability.type}" }
            abilityText = "$abilityText ${ability.text}"
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
            info["Weakness"] = "${weakness.type} ${weakness.value}"
        }
        if (card.resistance != null) {
            val resistance = card.resistance as PokeEffect
            info["Weakness"] = "${resistance.type} ${resistance.value}"
        }
        if (card.cardText != null) { info["Card Text"] = card.cardText as String }
        if (card.rarity != null) { info["Rarity"] = card.rarity as String}
        info["Series"] = card.series
        info["Set"] = card.set

        // TODO: pass this field only if not in the search (not correct/from db in search)
        // info["Date First Added"] = card.dateAdded // testing purposes only
        info["Supertype"] = card.supertype
        info["Subtype"] = card.subtype

        return info
    }
}