package com.physidex.physidex.database.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.physidex.physidex.database.daos.DeckDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.entities.PokeDeckInfoEntity

class PokeDeckRepository(private val deckDao: DeckDao) {

    // execute queries asynchronously
    val allDecks: LiveData<List<PokeDeckInfoEntity>> = deckDao.getAllDecks()
    //val allCards: LiveData<List<PokeDeckInfoEntity>>

    @WorkerThread
    suspend fun insert(deck: PokeDeckInfoEntity) {
        deckDao.newDeck(deck)
    }

    @WorkerThread
    suspend fun getDeck(deckId: Int) : LiveData<PokeDeckInfoEntity> {
        return deckDao.getDeckInfo(deckId)
    }

    @WorkerThread
    fun getCards(deckId: Int) : LiveData<List<FullPokeCard>> {
        return deckDao.getCardsQuery(deckId)
    }

    @WorkerThread
    fun getCardCopies(deckId: Int) : LiveData<List<DeckDao.CardWithNumCopies>> {
        return deckDao.getCardCopies(deckId)
    }

    @WorkerThread
    fun updateDeck(deckId: Int, deckName: String, deckSize: Int) {
        deckDao.updateDeckInfo(deckId, deckName, deckSize)
    }

    @WorkerThread
    fun deleteDeck(deckId: Int) {
        deckDao.removeAllCards(deckId)
        deckDao.deleteDeckInfo(deckId)
    }

//    @WorkerThread
//    suspend fun getCards(deckId: Int) : LiveData<List<DeckDao.CardWithNumCopies>> {
//        val rawCards: LiveData<List<DeckDao.CardWithNumCopies>> = deckDao.getCardsQuery(deckId)
////        val cards: LiveData<List<FullPokeCard>> = Transformations.map(rawCards) {
////            cards -> putIntoFullPokeCard(cards)
////        }
//        return rawCards
//    }

//    fun putIntoFullPokeCard(cardWithCopies: List<DeckDao.CardWithNumCopies>) : List<FullPokeCard> {
//        var fullCards: MutableList<FullPokeCard> = mutableListOf()
//        for (card in cardWithCopies) {
//            val pokeCardWithNum = card.card
//            pokeCardWithNum.setNumCopiesPerDeck(card.numCopies)
//            fullCards.add(pokeCardWithNum)
//        }
//        return fullCards
//    }

}