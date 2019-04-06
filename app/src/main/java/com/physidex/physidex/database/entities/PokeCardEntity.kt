package com.physidex.physidex.database.entities

import androidx.room.*
import io.pokemontcg.model.*

data class PokeEffect(
    var type: String,
    var value: String
)

fun consolidateEffects(effects: List<Effect>?): PokeEffect? {
    if (effects != null) {
        if (effects.size == 1) {
            return PokeEffect(effects[0].type.toString(), effects[0].value)
        } else if (effects.size > 1) {
            var types = ""
            for (effect in effects) {
                types += effect.type.toString()
            }
            return PokeEffect(types, effects[0].value)
        }
    }
    return null
}

@Entity
class PokeCardEntity
constructor(card: Card) {

    @PrimaryKey(autoGenerate = true) var id: Int = 0
    @ColumnInfo(name = "card_name") var cardName: String = card.name
    @ColumnInfo(name = "national_pokedex_number") var nationalDexNum: Int? = card.nationalPokedexNumber
    @ColumnInfo(name = "image_url") var imageUrl: String = card.imageUrl
    @ColumnInfo(name = "image_url_hi_res") var imageUrlHiRes: String = card.imageUrlHiRes
    @ColumnInfo(name = "type1") var type1: String? = (card.types?.getOrNull(0))?.toString()
    @ColumnInfo(name = "type2") var type2: String? = (card.types?.getOrNull(0))?.toString()
    @ColumnInfo(name = "supertype") var supertype: SuperType = card.supertype
    @ColumnInfo(name = "subtype") var subtype: SubType = card.subtype
    @ColumnInfo(name = "evolves_from") var evolvesFrom: String? = card.evolvesFrom
    @ColumnInfo(name = "hp") var hp: Int? = card.hp
    @ColumnInfo(name = "retreat_cost") var retreatCost: Int? = card.retreatCost?.size
    @ColumnInfo(name = "number_in_set") var numberInSet: String = card.number
    @ColumnInfo(name = "rarity") var rarity: String? = card.rarity
    @ColumnInfo(name = "series") var series: String = card.series
    @ColumnInfo(name = "set") var set: String = card.set
    @ColumnInfo(name = "card_text") var cardText: String? = card.text?.joinToString(",")
    @Embedded var weakness: PokeEffect? = consolidateEffects(card.weaknesses)
    @Embedded var resistance: PokeEffect? = consolidateEffects(card.resistances)
    @Embedded var ability: Ability? = card.ability
    @ColumnInfo(name = "num_copies") var numCopies: Int = 1
}