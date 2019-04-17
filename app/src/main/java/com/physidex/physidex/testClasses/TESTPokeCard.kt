package com.physidex.physidex.testClasses

import  io.pokemontcg.model.*

class TESTPokeCard(card: Card) : TESTGenericCard
{
    override var cardName: String = card.name
    override var numOwned: Int = 1
    override var numCopiesInDeck: Int = 1
    override var imageUrl: String = card.imageUrl
    private var mCard: Card = card
}