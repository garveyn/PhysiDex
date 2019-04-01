package com.physidex.physidex.database.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.physidex.physidex.database.daos.PokeCardDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.entities.PokeCardEntity

class PokeCardRepository(private val cardDao: PokeCardDao) {

    // execute query asynchronously
    val allCards: LiveData<List<FullPokeCard>> = cardDao.getFullCards()

    // on a non-UI thread, add card into the db
    @WorkerThread
    suspend fun insert(card: FullPokeCard) {
        cardDao.addCard(card)
    }
}