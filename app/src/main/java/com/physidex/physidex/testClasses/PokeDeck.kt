package com.physidex.physidex.testClasses

import java.util.*

class PokeDeck(arrayList: ArrayList<GenericCard>, size: Int, name: String) : Deck {

    override var deckName: String = name
    override var deckList: ArrayList<GenericCard> = arrayList
    override val requiredSize: Int = size
    override val creationDate: Date = GregorianCalendar().time
    override var lastModifiedDate: Date = creationDate
    override val isReadyToPlay : Boolean
        get() = deckList.size == requiredSize
    override val cardsNeededToPlay: Int
        get() = requiredSize - deckList.size
    val mMaxDuplicates: Int = this.requiredSize / 15
    val mPrizeCount: Int = this.requiredSize / 10
    var mPrizeCardGuesses = ArrayList<GenericCard>()

    // Constructors
    // Decks default to 60 TODO make it more if arraylist is bigger than 60
    constructor(arrayList: ArrayList<GenericCard>) : this(arrayList, 60, "newDeck")

    // Other Functions

    // Edit Decklist TODO Implement the following functions
    override fun addCard(newCards: ArrayList<GenericCard>) : Boolean
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

    override fun addCard(newCard: GenericCard) : Boolean {
        if (!isReadyToPlay) {
            return deckList.add(newCard)
        }
        return false
    }

    override fun removeCard(cardsToRemove: ArrayList<GenericCard>): Boolean {
        TODO("not implemented")
    }

    override fun removeCard(cardToRemove: GenericCard): Boolean {
        TODO("not implemented")
    }

    // Ingame functions
    override fun drawCard(cardToDraw: GenericCard) {}
    override fun drawCard(cardsToDraw: ArrayList<GenericCard>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}