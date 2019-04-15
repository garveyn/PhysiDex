package com.physidex.physidex.database.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.physidex.physidex.database.PhysiDexDatabase
import com.physidex.physidex.database.daos.CardDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.repositories.PokeCardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): CardViewModel(application) {

    private val repository: PokeCardRepository
    val allCardIds: LiveData<List<CardDao.CopiesPerId>>

    init {
        val cardDao = PhysiDexDatabase.getDatabase(application, scope).fullCardDao()
        repository = PokeCardRepository(cardDao)
        allCardIds = repository.allCardIds
    }

    fun insert(card: FullPokeCard, previousNumberOfCopies: Int) = scope.launch(Dispatchers.IO) {

        if (previousNumberOfCopies == 0 ) {
            repository.insert(card)
        } else {
            repository.update(card)
        }
    }

}