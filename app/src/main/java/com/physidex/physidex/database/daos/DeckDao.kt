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

    @Query("UPDATE PokeCardPerDeckEntity SET num_copies = :newNumCopies " +
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
    @Query("SELECT PokeCardEntity.*, PokeCardPerDeckEntity.num_copies FROM PokeCardEntity INNER JOIN PokeCardPerDeckEntity ON" +
            "PokeCardEntity.id=PokeCardPerDeckEntity.card_id " +
            "WHERE PokeCardPerDeckEntity.deck_id=:deckId")
    abstract fun getCardsQuery(deckId: Int): MutableLiveData<List<FullPokeCard>>
}