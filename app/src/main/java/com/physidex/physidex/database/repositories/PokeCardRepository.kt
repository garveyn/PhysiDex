package com.physidex.physidex.database.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.physidex.physidex.database.daos.FullCardDao
import com.physidex.physidex.database.entities.FullPokeCard

class PokeCardRepository(private val cardDao: FullCardDao) {

    // execute query asynchronously
    val allCards: LiveData<List<FullPokeCard>> = cardDao.getFullCards()

    // on a non-UI thread, add card into the db
    @WorkerThread
    suspend fun insert(card: FullPokeCard) {
        cardDao.addCard(card)
    }
}