package com.physidex.physidex

class PokeDeck(arrayList: ArrayList<PokeCard>, size: Int) : Deck() {

    // Member Variables
    private var mDeckList: ArrayList<PokeCard> = arrayList
    private val mMaxSize: Int = size
    private val mMaxDuplicates: Int = mMaxSize / 15
    private val mPrizeCount: Int = mMaxSize / 10
    private var mPrizeCardGuesses = ArrayList<PokeCard>()

    // Constructors
    // Decks default to 60
    constructor(arrayList: ArrayList<PokeCard>) : this(arrayList, 60)

    // Getters & Setters
    fun getDeckSize() : Int {return mMaxSize}
    fun getDeckList() : ArrayList<PokeCard> {return mDeckList}
    fun getMaxDuplicates() : Int {return mMaxDuplicates}
    fun getPrizeCardCount() : Int {return mPrizeCount}
    fun getPrizeCardGuesses() : ArrayList<PokeCard> {return mPrizeCardGuesses}


    fun setDeckList(newList: ArrayList<PokeCard>) {mDeckList = newList}
    fun setPrizeCardGuesses(guesses: ArrayList<PokeCard>) {mPrizeCardGuesses = guesses}

    // Other Functions

    // Manipulate Decklist TODO Implement the following functions
    fun addCards(newCards: ArrayList<PokeCard>) {}
    fun addCards(newCard: PokeCard) {}
    fun removeCards(cardsToRemove: ArrayList<PokeCard>) {}
    fun removeCards(cardToRemove: PokeCard) {}

    fun exchangeCards(cardsToRemove: ArrayList<PokeCard>, newCards: ArrayList<PokeCard>) {
        removeCards(cardsToRemove)
        addCards(newCards)
    }

    fun exchangeCards(cardToRemove: PokeCard, newCard: PokeCard) {
        removeCards(cardToRemove)
        addCards(newCard)
    }
}