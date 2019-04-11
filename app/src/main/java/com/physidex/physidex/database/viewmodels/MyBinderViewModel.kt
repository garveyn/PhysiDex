package com.physidex.physidex.database.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import com.physidex.physidex.database.PhysiDexDatabase
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.repositories.PokeCardRepository

//TODO: have two different viewmodels for mybindermain and grid,
//  so that not all the data is being sent out at once.
// The main page will just send < the first ten cards for each category.
// The grid page will have one variable, Cards,
// and update what the query is based on the request.
class MyBinderViewModel(application: Application): CardViewModel(application) {

    private val repository: PokeCardRepository
    val allCards: LiveData<List<FullPokeCard>>
    val allCardsByDate: LiveData<List<FullPokeCard>>

    init {
        val cardDao = PhysiDexDatabase.getDatabase(application, scope).fullCardDao()
        repository = PokeCardRepository(cardDao)
        allCards = repository.allCards
        allCardsByDate = repository.allCardsByDate
    }

    fun search(input: String) {

    }

}