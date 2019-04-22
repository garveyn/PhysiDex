package com.physidex.physidex.database.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.physidex.physidex.database.PhysiDexDatabase
import com.physidex.physidex.database.repositories.PokeCardRepository
import com.physidex.physidex.database.repositories.PokeDeckRepository

class HomeViewModel(application: Application) : CardViewModel(application) {

    private val cardRepository: PokeCardRepository
    private val deckRepository: PokeDeckRepository
    var numUniqueCards: LiveData<Int>
    var numTotalCards: LiveData<Int>
    var numDecks: LiveData<Int>

    init {
        val cardDao = PhysiDexDatabase.getDatabase(application, scope).fullCardDao()
        cardRepository = PokeCardRepository(cardDao)
        val deckDao = PhysiDexDatabase.getDatabase(application, scope).deckDao()
        deckRepository = PokeDeckRepository(deckDao)
        numDecks = deckRepository.numDecks
        numUniqueCards = Transformations.map(cardRepository.allCardIds) {
            cards -> cards.size
        }
        numTotalCards = Transformations.map(cardRepository.allCardIds) {
            cards -> countCards(cards)
        }
    }
}