package com.physidex.physidex.database.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.physidex.physidex.database.daos.CardDao
import com.physidex.physidex.database.entities.FullPokeCard

class PokeCardRepository(private val cardDao: CardDao) {

    // execute queries asynchronously
    val allCards: LiveData<List<FullPokeCard>> = cardDao.getFullCards()
    val allCardIds: LiveData<List<CardDao.CopiesPerId>> = cardDao.getAllIds()
    val allCardsByDate: LiveData<List<FullPokeCard>> = cardDao.getCardsByDate()

    // on a non-UI thread, add card into the db
    @WorkerThread
    suspend fun insert(card: FullPokeCard) {
        cardDao.addCard(card)
    }

    @WorkerThread
    suspend fun update(card: FullPokeCard) {
        cardDao.updateCard(card.pokeCard.id, card.pokeCard.numCopies)
    }

    @WorkerThread
    suspend fun search(input: String): List<FullPokeCard> {
        return cardDao.searchCardName(input)
    }
}