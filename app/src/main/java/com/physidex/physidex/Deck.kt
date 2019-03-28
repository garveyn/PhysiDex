package com.physidex.physidex

import java.util.*


abstract class Deck(arrayList: ArrayList<GenericCard>, size: Int, name: String){
    var mDeckList: ArrayList<GenericCard> = arrayList
    val mMaxSize: Int = size
    var mDeckName: String = name
    val creationDate: Date = GregorianCalendar.getInstance().time
    var mLastModifiedDate = creationDate
    val isReadyToPlay : Boolean
        get() = mDeckList.size == mMaxSize


    abstract fun addCards(newCards: ArrayList<GenericCard>)
    abstract fun addCards(newCard: GenericCard)
    abstract fun removeCards(cardsToRemove: ArrayList<GenericCard>)
    abstract fun removeCards(cardToRemove: GenericCard)

    fun exchangeCards(cardsToRemove: ArrayList<GenericCard>, newCards: ArrayList<GenericCard>) {
        removeCards(cardsToRemove)
        addCards(newCards)
    }

    fun exchangeCards(cardToRemove: GenericCard, newCard: GenericCard) {
        removeCards(cardToRemove)
        addCards(newCard)
    }

    // Ingame Functions
    abstract fun drawCard(cardToDraw: GenericCard)
}