package com.physidex.physidex

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

    fun buildDecks() : ArrayList<Deck> {


        var arrayListOfDecks = ArrayList<Deck>()
        var arrCard = ArrayList<GenericCard>()

        for (card in cards)
        {
            arrCard.add(PokeCard(card))
        }

        var salamanceDeck = PokeDeck(ArrayList())

        for (card in arrCard)
        {
            if (!salamanceDeck.isReadyToPlay) {
                salamanceDeck.addCard(card)
            } else {
                salamanceDeck.deckName = "Deck ${card.cardName}"
                arrayListOfDecks.add(salamanceDeck)
                salamanceDeck = PokeDeck(ArrayList())
            }
        }

        if (salamanceDeck.deckList.size > 0)
        {
            salamanceDeck.deckName = "Deck ${salamanceDeck.deckList[0].cardName}"
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