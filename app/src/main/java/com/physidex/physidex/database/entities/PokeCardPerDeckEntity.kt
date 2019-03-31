package com.physidex.physidex.database.entities

import androidx.room.*

@Entity(primaryKeys = ["deck_id", "card_id"],
        foreignKeys =
        [
            ForeignKey(
                    entity = PokeDeckInfoEntity::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("deck_id")
            ),
            ForeignKey(
                    entity = PokeCardEntity::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("card_id")
            )
        ]
)
class PokeCardPerDeckEntity (cardId: Int, deckId: Int){
    @ColumnInfo(name = "card_id") var cardId: Int = cardId
    @ColumnInfo(name = "desk_id") var deckId: Int = deckId
}



