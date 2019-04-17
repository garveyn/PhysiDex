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

    @Insert
    abstract fun newDeck(deck: PokeDeckInfoEntity)

    @Query("SELECT * FROM Poke_Deck_Info")
    abstract fun getAllDecks(): LiveData<List<PokeDeckInfoEntity>>

    fun addCard(deckId: Int, cardId: String, numCopies: Int) {
        val join = PokeCardPerDeckEntity(cardId, deckId, numCopies)
        insertCardPerDeck(join)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCardPerDeck(cardPerDeck: PokeCardPerDeckEntity)

//    fun removeCard(deckId: Int, cardId: String, numCopies: Int) {
//
//    }
//
//    @Delete
//    abstract fun removeCard()

    // custom delete deck query
    @Query("DELETE FROM Poke_Deck_Info WHERE id = :deckId")
    abstract fun deleteDeckInfo(deckId: Int)

    @Query("DELETE FROM Poke_Card_Per_Deck WHERE deck_id = :deckId")
    abstract fun removeAllCards(deckId: Int)

    @Query("UPDATE Poke_Card_Per_Deck SET num_copies = :newNumCopies " +
            "WHERE card_id = :cardId AND deck_id = :deckId")
    abstract fun updateNumCopies(cardId: String, deckId: Int, newNumCopies: Int)
//
//    fun getCards(deckId: Int): LiveData<List<FullPokeCard>> {
//        var cards: MutableLiveData<List<FullPokeCard>> = getCardsQuery(deckId)
//        for (card in cards) {
//
//        }
//    }

    @Query("SELECT * FROM Poke_Deck_Info WHERE id == :deckId")
    abstract fun getDeckInfo(deckId: Int): LiveData<PokeDeckInfoEntity>

    @Query("UPDATE Poke_Deck_Info SET deck_name = :deckName, required_size = :deckSize " +
            "WHERE id == :deckId")
    abstract fun updateDeckInfo(deckId: Int, deckName: String, deckSize: Int)

    data class CardWithNumCopies(var id: String, var num_copies: Int)

    @Transaction
    @Query("SELECT Poke_Card.* " +
            "FROM Poke_Card INNER JOIN Poke_Card_Per_Deck ON " +
            "Poke_Card_Per_Deck.card_id=Poke_Card.id " +
            "WHERE Poke_Card_Per_Deck.deck_id == :deckId")
    abstract fun getCardsQuery(deckId: Int): LiveData<List<FullPokeCard>>

    @Transaction
    @Query("SELECT Poke_Card.id, Poke_Card_Per_Deck.num_copies " +
            "FROM Poke_Card INNER JOIN Poke_Card_Per_Deck ON " +
            "Poke_Card_Per_Deck.card_id=Poke_Card.id " +
            "WHERE Poke_Card_Per_Deck.deck_id == :deckId")
    abstract fun getCardCopies(deckId: Int): LiveData<List<CardWithNumCopies>>

}