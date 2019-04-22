package com.physidex.physidex.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import io.pokemontcg.model.Attack
import io.pokemontcg.model.Type

/**
 * An entity that holds information for an attack listed on a card.
 *
 * Each PokeAttachEntity is connected to a single card. Each variable is a column in the database.
 *
 * @param name The name of the attack
 * @param text The text description of the attack
 * @param cost The type and amount of energy required to use this attack
 * @param damage How much damage this attack can do
 * @param convEnergyCost The number of energy cards needed to use this attack
 * @param cardId The id of the card this attack is attached to, which represents a foreign key
 * in the database
 */
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

    // A secondary constructor to be manually called with an Attack as the parameter.
    @Ignore
    constructor(attack: Attack, cardId: String) :
            this(attack.name, attack.text, attack.cost?.joinToString(",") ?: "",
                    attack.damage, attack.convertedEnergyCost, cardId)
}