package com.physidex.physidex

import java.util.*


interface Deck{
    var deckList: ArrayList<GenericCard>
    val requiredSize: Int
    var deckName: String
    val creationDate: Date
    var lastModifiedDate: Date
    val isReadyToPlay : Boolean
    val cardsNeededToPlay: Int


    fun addCard(newCards: ArrayList<GenericCard>) : Boolean
    fun addCard(newCard: GenericCard) : Boolean
    fun removeCard(cardsToRemove: ArrayList<GenericCard>) : Boolean
    fun removeCard(cardToRemove: GenericCard) : Boolean

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
    fun drawCard(cardToDraw: GenericCard)
    fun drawCard(cardsToDraw: ArrayList<GenericCard>)
}