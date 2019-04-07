package com.physidex.physidex.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import io.pokemontcg.model.Attack
import io.pokemontcg.model.Type

@Entity(foreignKeys =
    [
        ForeignKey(
                entity = PokeCardEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("card_id"),
                onDelete = CASCADE
        )
    ]
)
class PokeAttackEntity constructor(attack: Attack, cardId: String){

    @PrimaryKey(autoGenerate = true) var id: Int = 0
    @ColumnInfo(name = "name") var name: String = attack.name
    @ColumnInfo(name = "text") var text: String? = attack.text
    @ColumnInfo(name = "cost") var cost: String = attack.cost?.joinToString(",") ?: ""
    @ColumnInfo(name = "damage") var damage: String? = attack.damage
    @ColumnInfo(name = "converted_energy_cost") var convEnergyCost: Int = attack.convertedEnergyCost
    @ColumnInfo(name = "card_id") var cardId: String = cardId
}