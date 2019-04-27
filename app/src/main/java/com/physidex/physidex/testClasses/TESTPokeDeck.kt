package com.physidex.physidex.testClasses

import java.util.*

/**
 * Classes and objects that are not used in the final build. These were used to test with data
 * in the earlier days of the project. These are kept only for posterity.
 */
class TESTPokeDeck(arrayList: ArrayList<TESTGenericCard>, size: Int, name: String) : TESTDeck {

    override var deckName: String = name
    override var deckList: ArrayList<TESTGenericCard> = arrayList
    override val requiredSize: Int = size
    override val creationDate: Date = GregorianCalendar().time
    override var lastModifiedDate: Date = creationDate
    override val isReadyToPlay : Boolean
        get() = deckList.size == requiredSize
    override val cardsNeededToPlay: Int
        get() = requiredSize - deckList.size
    val mMaxDuplicates: Int = this.requiredSize / 15
    val mPrizeCount: Int = this.requiredSize / 10
    var mPrizeCardGuesses = ArrayList<TESTGenericCard>()

    // Constructors
    // Decks default to 60 TODO make it more if arraylist is bigger than 60
    constructor(arrayList: ArrayList<TESTGenericCard>) : this(arrayList, 60, "newDeck")

    // Other Functions

    // Edit Decklist TODO Implement the following functions
    override fun addCard(newCards: ArrayList<TESTGenericCard>) : Boolean
    {
        for (card in newCards)
        {
            if (!isReadyToPlay) // if deck isn't full
            {
                return addCard(card)
            }
        }
        return false
    }

    override fun addCard(newCard: TESTGenericCard) : Boolean {
        if (!isReadyToPlay) {
            return deckList.add(newCard)
        }
        return false
    }

    override fun removeCard(cardsToRemove: ArrayList<TESTGenericCard>): Boolean {
        TODO("not implemented")
    }

    override fun removeCard(cardToRemove: TESTGenericCard): Boolean {
        TODO("not implemented")
    }

    // Ingame functions
    override fun drawCard(cardToDraw: TESTGenericCard) {}
    override fun drawCard(cardsToDraw: ArrayList<TESTGenericCard>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}