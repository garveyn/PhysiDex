package com.physidex.physidex

import java.util.*


abstract class Deck(arrayList: ArrayList<GenericCard>, size: Int, name: String){
    var deckList: ArrayList<GenericCard> = arrayList
    val requiredSize: Int = size
    var deckName: String = name
    val creationDate: Date = GregorianCalendar.getInstance().time
    var lastModifiedDate = creationDate
    val isReadyToPlay : Boolean
        get() = deckList.size == requiredSize
    val cardsNeededToPlay: Int
        get() = requiredSize - deckList.size


    abstract fun addCard(newCards: ArrayList<GenericCard>) : Boolean
    abstract fun addCard(newCard: GenericCard) : Boolean
    abstract fun removeCard(cardsToRemove: ArrayList<GenericCard>) : Boolean
    abstract fun removeCard(cardToRemove: GenericCard) : Boolean

    fun exchangeCard(cardsToRemove: ArrayList<GenericCard>, newCards: ArrayList<GenericCard>) : Boolean {
        val removed = removeCard(cardsToRemove)
        val added = addCard(newCards)
        return removed && added
    }

    fun exchangeCard(cardToRemove: GenericCard, newCard: GenericCard) : Boolean {
        val removed = removeCard(cardToRemove)
        val added = addCard(newCard)
        return removed && added
    }

    // Ingame Functions
    abstract fun drawCard(cardToDraw: GenericCard)
    abstract fun drawCard(cardsToDraw: ArrayList<GenericCard>)
}