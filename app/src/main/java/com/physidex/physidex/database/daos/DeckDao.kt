package com.physidex.physidex.database.daos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.physidex.physidex.database.entities.FullPokeCard
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

    @Insert
    abstract fun insertCardPerDeck(cardPerDeck: PokeCardPerDeckEntity)

//    fun removeCard(deckId: Int, cardId: String, numCopies: Int) {
//
//    }
//
//    @Delete
//    abstract fun removeCard()

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


    @Transaction
    @Query("SELECT * FROM Poke_Card " +
            "INNER JOIN Poke_Card_Per_Deck ON " +
            "Poke_Card.id=Poke_Card_Per_Deck.card_id " +
            "WHERE Poke_Card_Per_Deck.deck_id == :deckId")
    abstract fun getCardsQuery(deckId: Int): LiveData<List<FullPokeCard>>
}