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
constructor(id: String, cardName: String, nationalDexNum: Int?, imageUrl: String,
            imageUrlHiRes: String, type1: String?, type2: String?, supertype: String, subtype: String,
            evolvesFrom: String?, hp: Int?, retreatCost: Int?, numberInSet: String,
            rarity: String?, series: String, set: String, cardText: String?,
            weakness: PokeEffect?, resistance: PokeEffect?, ability: Ability?, numCopies: Int) {

    @PrimaryKey var id: String = id
    @ColumnInfo(name = "card_name") var cardName: String = cardName
    @ColumnInfo(name = "national_pokedex_number") var nationalDexNum: Int? = nationalDexNum
    @ColumnInfo(name = "image_url") var imageUrl: String = imageUrl
    @ColumnInfo(name = "image_url_hi_res") var imageUrlHiRes: String = imageUrlHiRes
    @ColumnInfo(name = "type1") var type1: String? = type1
    @ColumnInfo(name = "type2") var type2: String? = type2
    @ColumnInfo(name = "supertype") var supertype: String = supertype
    @ColumnInfo(name = "subtype") var subtype: String = subtype
    @ColumnInfo(name = "evolves_from") var evolvesFrom: String? = evolvesFrom
    @ColumnInfo(name = "hp") var hp: Int? = hp
    @ColumnInfo(name = "retreat_cost") var retreatCost: Int? = retreatCost
    @ColumnInfo(name = "number_in_set") var numberInSet: String = numberInSet
    @ColumnInfo(name = "rarity") var rarity: String? = rarity
    @ColumnInfo(name = "series") var series: String = series
    @ColumnInfo(name = "set") var set: String = set
    @ColumnInfo(name = "card_text") var cardText: String? = cardText
    @Embedded(prefix = "weakness_") var weakness: PokeEffect? = weakness
    @Embedded(prefix = "res_") var resistance: PokeEffect? = resistance
    @Embedded(prefix = "ability_") var ability: Ability? = ability
    @ColumnInfo(name = "num_copies") var numCopies: Int = numCopies

    @Ignore
    constructor(card: Card, numCopies: Int?):
            this(card.id, card.name, card.nationalPokedexNumber, card.imageUrl, card.imageUrlHiRes,
                    (card.types?.getOrNull(0))?.toString(),
                    (card.types?.getOrNull(1))?.toString(), card.supertype.toString(),
                    card.subtype.toString(), card.evolvesFrom, card.hp, card.retreatCost?.size,
                    card.number, card.rarity, card.series, card.set,
                    card.text?.joinToString(","), consolidateEffects(card.weaknesses),
                    consolidateEffects(card.resistances), card.ability, numCopies ?: 0)
}