package com.physidex.physidex.database.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.physidex.physidex.database.daos.DeckDao
import com.physidex.physidex.database.entities.PokeDeckInfoEntity

class PokeDeckRepository(private val deckDao: DeckDao) {

    // execute queries asynchronously
    val allDecks: LiveData<List<PokeDeckInfoEntity>> = deckDao.getAllDecks()

    @WorkerThread
    suspend fun insert(deck: PokeDeckInfoEntity) {
        deckDao.newDeck(deck)
    }

}