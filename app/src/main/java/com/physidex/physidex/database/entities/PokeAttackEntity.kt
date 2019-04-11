package com.physidex.physidex.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import io.pokemontcg.model.Attack
import io.pokemontcg.model.Type

@Entity(tableName = "Poke_Attack",
        foreignKeys =
    [
        ForeignKey(
                entity = PokeCardEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("card_id"),
                onDelete = CASCADE
        )
    ]
)
class PokeAttackEntity
constructor(name: String, text: String?, cost: String, damage: String?,
            convEnergyCost: Int, cardId: String) {

    @PrimaryKey(autoGenerate = true) var id: Int = 0
    @ColumnInfo(name = "name") var name: String = name
    @ColumnInfo(name = "text") var text: String? = text
    @ColumnInfo(name = "cost") var cost: String = cost
    @ColumnInfo(name = "damage") var damage: String? = damage
    @ColumnInfo(name = "converted_energy_cost") var convEnergyCost: Int = convEnergyCost
    @ColumnInfo(name = "card_id") var cardId: String = cardId

    @Ignore
    constructor(attack: Attack, cardId: String) :
            this(attack.name, attack.text, attack.cost?.joinToString(",") ?: "",
                    attack.damage, attack.convertedEnergyCost, cardId)
}