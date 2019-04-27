package com.physidex.physidex.testClasses

/**
 * Classes and objects that are not used in the final build. These were used to test with data
 * in the earlier days of the project. These are kept only for posterity.
 */
interface TESTGenericCard {
    var cardName: String
    var numOwned: Int
    var numCopiesInDeck: Int
    var imageUrl: String
}

/**
 * Classes and objects that are not used in the final build. These were used to test with data
 * in the earlier days of the project. These are kept only for posterity.
 */
interface TESTGenericPokeCard: TESTGenericCard {
    var cardType: String

}