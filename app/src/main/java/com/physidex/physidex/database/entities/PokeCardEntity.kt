package com.physidex.physidex.database.entities

import androidx.room.*
import io.pokemontcg.model.*

//data class PokeAttack (
//        var cost: String?,
//        var name: String,
//        var text: String?,
//        var damage: String?,
//        @ColumnInfo(name = "converted_energy_cost") var convertedEnergyCost: Int
//)

//@Entity(foreignKeys =
//    [
//        ForeignKey(
//                entity = PokeAttack::class,
//                parentColumns = arrayOf("aid"),
//                childColumns = arrayOf("attackId")
//        )
//    ]
//)

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
constructor(cardName: String, nationalPokedexNumber: Int?,
                           imageUrl: String, imageUrlHiRes: String,
                           types: List<Type>, supertype: SuperType,
                           subtype: SubType, evolvesFrom: String?,
                           hp: Int?, retreatCost: List<Type>? ,
                           number: String, rarity: String?,
                           series: String, set: String,
                           text: List<String>, attacks: List<Attack>?,
                           weaknesses: List<Effect>?, resistances: List<Effect>?,
                           ability: Ability?) {

    @PrimaryKey(autoGenerate = true) var id: Int = 0
    @ColumnInfo(name = "card_name") var cardName: String = cardName
    @ColumnInfo(name = "national_pokedex_number") var nationalDexNum: Int? = nationalPokedexNumber
    @ColumnInfo(name = "image_url") var imageUrl: String = imageUrl
    @ColumnInfo(name = "image_url_hi_res") var imageUrlHiRes: String = imageUrlHiRes
    @ColumnInfo(name = "types") var types: List<Type> = types
    @ColumnInfo(name = "supertype") var supertype: SuperType = supertype
    @ColumnInfo(name = "subtype") var subtype: SubType = subtype
    @ColumnInfo(name = "evolves_from") var evolvesFrom: String? = evolvesFrom
    @ColumnInfo(name = "hp") var hp: Int? = hp
    @ColumnInfo(name = "retreat_cost") var retreatCost: Int = retreatCost?.size ?: 0
    @ColumnInfo(name = "number_in_set") var numberInSet: String = number
    @ColumnInfo(name = "rarity") var rarity: String? = rarity
    @ColumnInfo(name = "series") var series: String = series
    @ColumnInfo(name = "set") var set: String = set
    @ColumnInfo(name = "card_text") var cardText: String = text.joinToString(",")
    @Embedded var weakness: PokeEffect? = consolidateEffects(weaknesses)
    @Embedded var resistance: PokeEffect? = consolidateEffects(resistances)
    @Embedded var ability: Ability? = ability
    @ColumnInfo(name = "num_copies") var numCopies: Int = 1
}