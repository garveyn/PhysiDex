package com.physidex.physidex.testClasses

import  io.pokemontcg.model.*

/**
 * Classes and objects that are not used in the final build. These were used to test with data
 * in the earlier days of the project. These are kept only for posterity.
 */
class TESTPokeCard(card: Card) : TESTGenericCard
{
    override var cardName: String = card.name
    override var numOwned: Int = 1
    override var numCopiesInDeck: Int = 1
    override var imageUrl: String = card.imageUrl
    private var mCard: Card = card
}