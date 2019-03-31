package com.physidex.physidex

import  io.pokemontcg.model.*

class PokeCard(card: Card) : GenericCard
{
    override var cardName: String = card.name
    override var numOwned: Int = 1
    override var numCopiesInDeck: Int = 1
    override var imageUrl: String = card.imageUrl
    private var mCard: Card = card
}