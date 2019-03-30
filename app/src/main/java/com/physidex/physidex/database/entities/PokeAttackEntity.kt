package com.physidex.physidex.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import io.pokemontcg.model.Type

@Entity(foreignKeys =
    [
        ForeignKey(
                entity = PokeCard::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("card_id"),
                onDelete = CASCADE
        )
    ]
)
class PokeAttackEntity constructor(cost: List<Type>?, name: String, text: String?, damage: String?,
                                   convertedEnergyCost: Int, cardId: Int){

    @PrimaryKey(autoGenerate = true) var aid: Int = 0
    @ColumnInfo(name = "name") var name: String = name
    @ColumnInfo(name = "text") var text: String? = text
    @ColumnInfo(name = "cost") var cost: String = cost?.joinToString(",") ?: ""
    @ColumnInfo(name = "damage") var damage: String? = damage
    @ColumnInfo(name = "converted_energy_cost") var convEnergyCost: Int = convertedEnergyCost
    @ColumnInfo(name = "card_id") var cardId: Int = cardId
}