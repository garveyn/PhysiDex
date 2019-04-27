package com.physidex.physidex.testClasses

import java.util.*

/**
 * Classes and objects that are not used in the final build. These were used to test with data
 * in the earlier days of the project. These are kept only for posterity.
 */
interface TESTDeck{
    var deckList: ArrayList<TESTGenericCard>
    val requiredSize: Int
    var deckName: String
    val creationDate: Date
    var lastModifiedDate: Date
    val isReadyToPlay : Boolean
    val cardsNeededToPlay: Int


    fun addCard(newCards: ArrayList<TESTGenericCard>) : Boolean
    fun addCard(newCard: TESTGenericCard) : Boolean
    fun removeCard(cardsToRemove: ArrayList<TESTGenericCard>) : Boolean
    fun removeCard(cardToRemove: TESTGenericCard) : Boolean

    fun exchangeCard(cardsToRemove: ArrayList<TESTGenericCard>, newCards: ArrayList<TESTGenericCard>) : Boolean {
        val removed = removeCard(cardsToRemove)
        val added = addCard(newCards)
        return removed && added
    }

    fun exchangeCard(cardToRemove: TESTGenericCard, newCard: TESTGenericCard) : Boolean {
        val removed = removeCard(cardToRemove)
        val added = addCard(newCard)
        return removed && added
    }

    // Ingame Functions
    fun drawCard(cardToDraw: TESTGenericCard)
    fun drawCard(cardsToDraw: ArrayList<TESTGenericCard>)
}