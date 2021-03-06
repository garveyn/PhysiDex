package com.physidex.physidex.database.entities

import androidx.room.*
import io.pokemontcg.model.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A data class to hold information on an Effect.
 *
 * This represents an effect, such as a weakness or strength, including the element that
 * triggers the effect and the how much it affects the player or opponent's card.
 */
data class PokeEffect(
    var type: String,
    var value: String
)

/**
 * This function combines a list of Effects into two strings in the data class PokeEffect.
 *
 * It is very rare for a card to have multiple effects, but in case they do, this will combine the
 * text of the effects so that it can be saved as one effect.
 *
 * @param effects A list of Effects for one card
 * @return a PokeEffect with the type and value combined
 */
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

/**
 * The class that represents the main info of a Pokemon Card in the database.
 *
 * This holds all of the base information of one card, as received from Pokemon TCG API.
 *
 * @param id The unique id of a card, based on the set its in and its number in the set.
 * The parameters following all represent the text on a Pokemon card.
 *
 */
@Entity(tableName = "Poke_Card")
class PokeCardEntity
constructor(id: String, cardName: String, nationalDexNum: Int?, imageUrl: String,
            imageUrlHiRes: String, type1: String?, type2: String?, supertype: String, subtype: String,
            evolvesFrom: String?, hp: Int?, retreatCost: Int?, numberInSet: String,
            rarity: String?, series: String, set: String, cardText: String?,
            weakness: PokeEffect?, resistance: PokeEffect?, ability: Ability?, dateAdded: String,
            numCopies: Int) {

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
    @ColumnInfo(name = "first_added") var dateAdded: String = dateAdded

    // A secondary constructor to initialize PokeCardEntity based on a Card.
    @Ignore
    constructor(card: Card, numCopies: Int?):
            this(card.id, card.name, card.nationalPokedexNumber, card.imageUrl, card.imageUrlHiRes,
                    (card.types?.getOrNull(0))?.toString(),
                    (card.types?.getOrNull(1))?.toString(), card.supertype.toString(),
                    card.subtype.toString(), card.evolvesFrom, card.hp, card.retreatCost?.size,
                    card.number, card.rarity, card.series, card.set,
                    card.text?.joinToString(","), consolidateEffects(card.weaknesses),
                    consolidateEffects(card.resistances), card.ability, "",
                    numCopies ?: 0) {

        // First Added will be automatically set to the current date.
        // If this card is a duplicate, only numCopies will be updated in the database, so
        // this field will only be saved if this card is inserted for the first time.
        val currentDate = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        this.dateAdded = format.format(currentDate).toString()

    }
}