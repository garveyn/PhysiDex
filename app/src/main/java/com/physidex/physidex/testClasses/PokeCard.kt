package com.physidex.physidex.testClasses

import  io.pokemontcg.model.*

//@Deprecated(
//        "Test class. Replaced by use of database entities FullPokeCard and PokeCardEntity")
class PokeCard(card: Card) : GenericCard
{
    override var cardName: String = card.name
    override var numOwned: Int = 1
    override var numCopiesInDeck: Int = 1
    override var imageUrl: String = card.imageUrl
    private var mCard: Card = card
}