package com.physidex.physidex.database.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.physidex.physidex.database.PhysiDexDatabase
import com.physidex.physidex.database.daos.CardDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.repositories.PokeCardRepository
import com.physidex.physidex.database.repositories.PokeDeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//TODO: have two different viewmodels for mybindermain and grid,
//  so that not all the data is being sent out at once.
// The main page will just send < the first ten cards for each category.
// The grid page will have one variable, Cards,
// and update what the query is based on the request.
class MyBinderViewModel(application: Application, deckId: Int = -1): CardViewModel(application) {

    private val cardRepository: PokeCardRepository
    private val deckRepository: PokeDeckRepository
    val allCards: LiveData<List<FullPokeCard>>
    val allCardsByDate: LiveData<List<FullPokeCard>>
    val trainerCards: LiveData<List<FullPokeCard>>
    var deckCardCopies:  LiveData<List<CardDao.CopiesPerId>>

    init {
        val cardDao = PhysiDexDatabase.getDatabase(application, scope).fullCardDao()
        cardRepository = PokeCardRepository(cardDao)
        allCards = cardRepository.allCards
        allCardsByDate = cardRepository.allCardsByDate
        trainerCards = cardRepository.trainerCards

        val deckDao = PhysiDexDatabase.getDatabase(application, scope).deckDao()
        deckRepository = PokeDeckRepository(deckDao)
        deckCardCopies = deckRepository.deckCopies
        getDeckCopies(deckId)

    }

    fun search(input: String) {

    }

    fun getDeckCopies(deckId: Int) = scope.launch(Dispatchers.IO) {

        // deckCardCopies is already set to an empty result (searching with -1),
        // no need to query it again.
        if (deckId != -1) {
            deckCardCopies = deckRepository.getCardCopies(deckId)
        }
    }

}