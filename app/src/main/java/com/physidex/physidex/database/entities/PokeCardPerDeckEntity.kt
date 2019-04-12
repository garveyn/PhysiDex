package com.physidex.physidex.database.entities

import androidx.room.*

@Entity(tableName = "Poke_Card_Per_Deck",
        primaryKeys = ["deck_id", "card_id"],
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
class PokeCardPerDeckEntity (cardId: String, deckId: Int, numCopies: Int = 1) {
    @ColumnInfo(name = "card_id") var cardId: String = cardId
    @ColumnInfo(name = "deck_id") var deckId: Int = deckId
    @ColumnInfo(name = "num_copies") var numCopies: Int = numCopies
}



