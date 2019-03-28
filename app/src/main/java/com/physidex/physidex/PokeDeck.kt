package com.physidex.physidex

class PokeDeck(arrayList: ArrayList<GenericCard>, size: Int, name: String)
    : Deck(arrayList, size, name) {

    // Member Variables
    val mMaxDuplicates: Int = this.mMaxSize / 15
    val mPrizeCount: Int = this.mMaxSize / 10
    var mPrizeCardGuesses = ArrayList<GenericCard>()

    // Constructors
    // Decks default to 60 TODO make it more if arraylist is bigger than 60
    constructor(arrayList: ArrayList<GenericCard>) : this(arrayList, 60, "newDeck")

    // Other Functions

    // Edit Decklist TODO Implement the following functions
    override fun addCards(newCards: ArrayList<GenericCard>)
    {
        for (card in newCards)
        {
            if (!isReadyToPlay) // if deck isn't full
            {
                addCards(card)
            }
        }
    }

    override fun addCards(newCard: GenericCard) {
        if (!isReadyToPlay) {
            mDeckList.add(newCard)
        }
    }
    override fun removeCards(cardsToRemove: ArrayList<GenericCard>) {}
    override fun removeCards(cardToRemove: GenericCard) {}


    // Ingame functions
    override fun drawCard(cardToDraw: GenericCard) {}
}