package com.physidex.physidex.database.daos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.entities.PokeCardEntity
import com.physidex.physidex.database.entities.PokeCardPerDeckEntity
import com.physidex.physidex.database.entities.PokeDeckInfoEntity

@Dao
abstract class DeckDao {

    // Insert statements

    @Insert
    abstract fun newDeck(deck: PokeDeckInfoEntity)

    fun addCard(deckId: Int, cardId: String, numCopies: Int = 1) {
        val join = PokeCardPerDeckEntity(cardId, deckId, numCopies)
        insertCardPerDeck(join)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCardPerDeck(cardPerDeck: PokeCardPerDeckEntity)

    // Another copy of a card is added
    @Query("UPDATE Poke_Card_Per_Deck SET num_copies = :newNumCopies " +
            "WHERE card_id = :cardId AND deck_id = :deckId")
    abstract fun updateNumCopies(cardId: String, deckId: Int, newNumCopies: Int)



    // Select statements

    @Query("SELECT * FROM Poke_Deck_Info")
    abstract fun getAllDecks(): LiveData<List<PokeDeckInfoEntity>>

    @Query("SELECT * FROM Poke_Deck_Info LIMIT 1")
    abstract fun getOneDeck(): LiveData<PokeDeckInfoEntity>

    @Query("SELECT * FROM Poke_Deck_Info WHERE id == :deckId")
    abstract fun getDeckInfo(deckId: Int): LiveData<PokeDeckInfoEntity>

    @Query("UPDATE Poke_Deck_Info SET deck_name = :deckName, required_size = :deckSize " +
            "WHERE id == :deckId")
    abstract fun updateDeckInfo(deckId: Int, deckName: String, deckSize: Int)

    @Transaction
    @Query("SELECT Poke_Card.* " +
            "FROM Poke_Card INNER JOIN Poke_Card_Per_Deck ON " +
            "Poke_Card_Per_Deck.card_id=Poke_Card.id " +
            "WHERE Poke_Card_Per_Deck.deck_id == :deckId")
    abstract fun getCardsQuery(deckId: Int): LiveData<List<FullPokeCard>>


    // Calculated fields for a deck

    @Query("SELECT COUNT(id) FROM Poke_Card_Per_Deck " +
            "INNER JOIN Poke_Card ON Poke_Card_Per_Deck.card_id=Poke_Card.id " +
            "WHERE Poke_Card.supertype == :type AND Poke_Card_Per_Deck.deck_id == :deckId")
    abstract fun getNumPerType(deckId: Int, type: String): LiveData<Int>

    @Query("SELECT COUNT(id) FROM Poke_Deck_Info")
    abstract fun getNumDecks(): LiveData<Int>

    @Transaction
    @Query("SELECT Poke_Card.id, Poke_Card_Per_Deck.num_copies AS numCopies " +
            "FROM Poke_Card INNER JOIN Poke_Card_Per_Deck ON " +
            "Poke_Card_Per_Deck.card_id=Poke_Card.id " +
            "WHERE Poke_Card_Per_Deck.deck_id == :deckId")
    abstract fun getCardCopies(deckId: Int): LiveData<List<CardDao.CopiesPerId>>

    @Query("SELECT Poke_Card.id, Poke_Card_Per_Deck.num_copies AS numCopies FROM Poke_Card "+
            "INNER JOIN Poke_Card_Per_Deck ON Poke_Card_Per_Deck.card_id=Poke_Card.id " +
            "WHERE Poke_Card.supertype == :type AND Poke_Card_Per_Deck.deck_id == :deckId")
    abstract fun getCopiesPerType(deckId: Int, type: String): LiveData<List<CardDao.CopiesPerId>>

    // Delete statements

    // custom delete deck query
    @Query("DELETE FROM Poke_Deck_Info WHERE id = :deckId")
    abstract fun deleteDeckInfo(deckId: Int)

    @Query("DELETE FROM Poke_Card_Per_Deck WHERE deck_id = :deckId")
    abstract fun removeAllCards(deckId: Int)

    @Delete
    abstract fun removeCard(card: PokeCardPerDeckEntity)


}