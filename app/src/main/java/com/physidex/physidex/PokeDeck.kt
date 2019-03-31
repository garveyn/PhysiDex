package com.physidex.physidex

class PokeDeck(arrayList: ArrayList<GenericCard>, size: Int, name: String)
    : Deck(arrayList, size, name) {

    // Member Variables
    val mMaxDuplicates: Int = this.requiredSize / 15
    val mPrizeCount: Int = this.requiredSize / 10
    var mPrizeCardGuesses = ArrayList<GenericCard>()
    override val isReadyToPlay: Boolean
        get() = mPrizeCount > 5

    // Constructors
    // Decks default to 60 TODO make it more if arraylist is bigger than 60
    constructor(arrayList: ArrayList<GenericCard>) : this(arrayList, 60, "newDeck")

    // Other Functions

    // Edit Decklist TODO Implement the following functions
    override fun addCard(newCards: ArrayList<GenericCard>)
    {
        for (card in newCards)
        {
            if (!isReadyToPlay) // if deck isn't full
            {
                return addCard(card)
            }
        }
    }

    override fun addCard(newCard: GenericCard) {
        if (!isReadyToPlay) {
            deckList.add(newCard)
        }
    }
    override fun removeCard(cardsToRemove: ArrayList<GenericCard>) {}
    override fun removeCard(cardToRemove: GenericCard) {}


    // Ingame functions
    override fun drawCard(cardToDraw: GenericCard) {}
    override fun drawCard(cardsToDraw: ArrayList<GenericCard>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}