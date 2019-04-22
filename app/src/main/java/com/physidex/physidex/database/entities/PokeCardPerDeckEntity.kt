package com.physidex.physidex.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

/**
 * A class for the intermediary table that connects cards to decks.
 *
 * Since classes and decks have a many to many relationship (i.e. one card could be in multiple
 * decks and each deck has multiple cards), this table connects the Poke_Deck_Info and Poke_Card
 * tables. If the deck or card in an entry is deleted, the connection will be deleted as well.
 *
 * @param cardId The id of a card, connected to a Poke_Card
 * @param deckId The id of a deck, connected to Poke_Deck_Info
 * @param numCopies The number of copies of this specific card in this specific deck
 */
@Entity(tableName = "Poke_Card_Per_Deck",
        primaryKeys = ["deck_id", "card_id"],
        foreignKeys =
        [
            ForeignKey(
                    entity = PokeDeckInfoEntity::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("deck_id"),
                    onDelete = CASCADE
            ),
            ForeignKey(
                    entity = PokeCardEntity::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("card_id"),
                    onDelete = CASCADE
            )
        ]
)
class PokeCardPerDeckEntity (cardId: String, deckId: Int, numCopies: Int = 1) {
    @ColumnInfo(name = "card_id") var cardId: String = cardId
    @ColumnInfo(name = "deck_id") var deckId: Int = deckId
    @ColumnInfo(name = "num_copies") var numCopies: Int = numCopies
}



