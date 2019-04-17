package com.physidex.physidex.testClasses

import com.physidex.physidex.database.entities.FullPokeCard
import io.pokemontcg.model.Card

object TestData {


    var cards = ArrayList<Card>()

    fun loadCards(list: List<Card>) {
        for (card in list)
        {
            cards.add(card)
        }
    }

    fun buildDecks() : ArrayList<TESTDeck> {


        var arrayListOfDecks = ArrayList<TESTDeck>()
        var arrCard = ArrayList<TESTGenericCard>()

        for (card in cards)
        {
            arrCard.add(TESTPokeCard(card))
        }

        var salamanceDeck = TESTPokeDeck(ArrayList())

        for (card in arrCard)
        {
            if (!salamanceDeck.isReadyToPlay) {
                salamanceDeck.addCard(card)
            } else {
                salamanceDeck.deckName = "TESTDeck ${card.cardName}"
                arrayListOfDecks.add(salamanceDeck)
                salamanceDeck = TESTPokeDeck(ArrayList())
            }
        }

        if (salamanceDeck.deckList.size > 0)
        {
            salamanceDeck.deckName = "TESTDeck ${salamanceDeck.deckList[0].cardName}"
            arrayListOfDecks.add(salamanceDeck)
        }


        print(arrCard.toString())

        return arrayListOfDecks

    }

    fun buildFullCards() : MutableList<FullPokeCard> {
        val list = mutableListOf<FullPokeCard>()
        for (card in cards) {
            list.add(FullPokeCard(card))
        }
        return list
    }
}