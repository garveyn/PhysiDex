package com.physidex.physidex.database.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.physidex.physidex.database.daos.CardDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class CardViewModel(application: Application): AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    protected val scope = CoroutineScope(coroutineContext)

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun countCards(cardCopies: List<CardDao.CopiesPerId>): Int {
        var cardCount = 0
        cardCopies.forEach {
            cardCount += it.numCopies
        }

        return cardCount
    }
}