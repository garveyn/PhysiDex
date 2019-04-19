package com.physidex.physidex.database.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import com.physidex.physidex.database.PhysiDexDatabase
import com.physidex.physidex.database.daos.CardDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.repositories.PokeCardRepository
import com.physidex.physidex.database.repositories.PokeDeckRepository

//TODO: have two different viewmodels for mybindermain and grid,
//  so that not all the data is being sent out at once.
// The main page will just send < the first ten cards for each category.
// The grid page will have one variable, Cards,
// and update what the query is based on the request.
class MyBinderViewModel(application: Application, deckId: Int? = null): CardViewModel(application) {

    private val cardRepository: PokeCardRepository
    private val deckRepository: PokeDeckRepository
    val allCards: LiveData<List<FullPokeCard>>
    val allCardsByDate: LiveData<List<FullPokeCard>>
    val deckCardCopies:  LiveData<List<CardDao.CopiesPerId>>?

    init {
        val cardDao = PhysiDexDatabase.getDatabase(application, scope).fullCardDao()
        cardRepository = PokeCardRepository(cardDao)
        allCards = cardRepository.allCards
        allCardsByDate = cardRepository.allCardsByDate

        val deckDao = PhysiDexDatabase.getDatabase(application, scope).deckDao()
        deckRepository = PokeDeckRepository(deckDao)
        if (deckId != null) {
            deckCardCopies = deckRepository.getCardCopies(deckId)
        } else {
            deckCardCopies = null
        }
    }

    fun search(input: String) {

    }

}