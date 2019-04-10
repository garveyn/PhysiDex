package com.physidex.physidex.database.viewmodels

import android.app.Application
import androidx.cardview.widget.CardView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.physidex.physidex.database.PhysiDexDatabase
import com.physidex.physidex.database.daos.FullCardDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.repositories.PokeCardRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

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

//    fun insert(card: FullPokeCard) = scope.launch(Dispatchers.IO) {
//        val previousNumCopies = card.pokeCard.numCopies
//        card.pokeCard.numCopies = previousNumCopies + 1
//
//        if (previousNumCopies == 0 ) {
//            repository.insert(card)
//        } else {
//            repository.update(card)
//        }
//    }

}