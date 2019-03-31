package com.physidex.physidex.database.repositories

import android.arch.lifecycle.LiveData
import androidx.annotation.WorkerThread
import com.physidex.physidex.database.daos.PokeCardDao
import com.physidex.physidex.database.entities.CardWithAttacks
import com.physidex.physidex.database.entities.PokeCardEntity

class PokeCardRepository(private val cardDao: PokeCardDao) {

    // execute query asynchronously
    val allCards: LiveData<List<CardWithAttacks>> = cardDao.getFullCards()

    // on a non-UI thread, add card into the db
    @WorkerThread
    suspend fun insert(card: CardWithAttacks) {
        cardDao.addCard(card)
    }
}