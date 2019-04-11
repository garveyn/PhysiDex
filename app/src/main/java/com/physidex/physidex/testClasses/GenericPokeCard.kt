package com.physidex.physidex.testClasses

interface GenericCard {
    var cardName: String
    var numOwned: Int
    var numCopiesInDeck: Int
    var imageUrl: String
}

interface GenericPokeCard: GenericCard {
    var cardType: String

}