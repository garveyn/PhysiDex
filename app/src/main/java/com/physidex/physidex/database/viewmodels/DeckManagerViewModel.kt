package com.physidex.physidex.database.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import com.physidex.physidex.database.PhysiDexDatabase
import com.physidex.physidex.database.entities.PokeDeckInfoEntity
import com.physidex.physidex.database.repositories.PokeDeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeckManagerViewModel(application: Application): CardViewModel(application) {

    private val repository: PokeDeckRepository
    val allDecks: LiveData<List<PokeDeckInfoEntity>>

    init {
        val deckDao = PhysiDexDatabase.getDatabase(application, scope).deckDao()
        repository = PokeDeckRepository(deckDao)
        allDecks = repository.allDecks
    }

    fun insert(deckName: String = "New Deck", requiredSize: Int = 60) = scope.launch(Dispatchers.IO) {
        val newDeck = PokeDeckInfoEntity(deckName, requiredSize)
        repository.insert(newDeck)
    }

    fun delete(deckId: Int) = scope.launch(Dispatchers.IO) {
        repository.deleteDeck(deckId)
    }
}