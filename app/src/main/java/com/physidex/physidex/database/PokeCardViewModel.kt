package com.physidex.physidex.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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
    val allWords: LiveData<List<FullPokeCard>>

    init {
        val cardDao = PhysiDexDatabase.getDatabase(application).pokeCardDao()
        repository = PokeCardRepository(cardDao)
        allWords = repository.allCards
    }

    fun insert(card: FullPokeCard) = scope.launch(Dispatchers.IO) {
        repository.insert(card)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

}