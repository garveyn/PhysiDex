package com.physidex.physidex.testClasses

interface TESTGenericCard {
    var cardName: String
    var numOwned: Int
    var numCopiesInDeck: Int
    var imageUrl: String
}

interface TESTGenericPokeCard: TESTGenericCard {
    var cardType: String

}