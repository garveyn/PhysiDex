package com.physidex.physidex.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.physidex.physidex.database.daos.FullCardDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.repositories.PokeCardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PokeCardViewModel(application: Application): AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: PokeCardRepository
    val allCards: LiveData<List<FullPokeCard>>
    val allCardIds: LiveData<List<FullCardDao.CopiesPerId>>

    init {
        val cardDao = PhysiDexDatabase.getDatabase(application, scope).fullCardDao()
        repository = PokeCardRepository(cardDao)
        allCards = repository.allCards
        allCardIds = repository.allCardIds
    }

    fun insert(card: FullPokeCard) = scope.launch(Dispatchers.IO) {
        val previousNumCopies = card.pokeCard.numCopies
        card.pokeCard.numCopies = previousNumCopies + 1

        if (previousNumCopies == 0 ) {
            repository.insert(card)
        } else {
            repository.update(card)
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}