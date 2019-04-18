package com.physidex.physidex.database.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.physidex.physidex.database.daos.CardDao
import com.physidex.physidex.database.entities.FullPokeCard
import java.text.SimpleDateFormat
import java.util.*

class PokeCardRepository(private val cardDao: CardDao) {

    // execute queries asynchronously
    val allCards: LiveData<List<FullPokeCard>> = cardDao.getFullCards()
    val allCardIds: LiveData<List<CardDao.CopiesPerId>> = cardDao.getAllIds()
    val allCardsByDate: LiveData<List<FullPokeCard>> = cardDao.getCardsByDate()

    // on a non-UI thread, add card into the db
    @WorkerThread
    suspend fun insert(card: FullPokeCard) {
        val currentDate = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        card.pokeCard.dateAdded = format.format(currentDate).toString()
        cardDao.addCard(card)
    }

    @WorkerThread
    suspend fun update(cardId: String, numCopies: Int) {
        cardDao.updateCard(cardId, numCopies)
    }

    @WorkerThread
    suspend fun search(input: String): List<FullPokeCard> {
        return cardDao.searchCardName(input)
    }

    @WorkerThread
    suspend fun removeAllCopies(cardId: String) {
        cardDao.deleteCard(cardId)
    }
}