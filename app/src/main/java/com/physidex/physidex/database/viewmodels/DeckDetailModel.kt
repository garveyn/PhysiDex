package com.physidex.physidex.database.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import com.physidex.physidex.database.PhysiDexDatabase
import com.physidex.physidex.database.daos.DeckDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.entities.PokeDeckInfoEntity
import com.physidex.physidex.database.repositories.PokeDeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeckDetailModel(application: Application, deckId: Int): CardViewModel(application) {

    private val repository: PokeDeckRepository
    lateinit var deckInfo: LiveData<PokeDeckInfoEntity>
    lateinit var deckCards: LiveData<List<DeckDao.CardWithNumCopies>>

    init {
        val deckDao = PhysiDexDatabase.getDatabase(application, scope).deckDao()
        repository = PokeDeckRepository(deckDao)
        getDeck(deckId)
        getCards(deckId)
    }

    fun getDeck(deckId: Int) = scope.launch(Dispatchers.IO) {
        deckInfo = repository.getDeck(deckId)
    }

    fun getCards(deckId: Int) = scope.launch(Dispatchers.IO) {
        deckCards = repository.getCards(deckId)
    }
}