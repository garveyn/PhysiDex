package com.physidex.physidex

import io.pokemontcg.Pokemon
import io.pokemontcg.model.Card

object ArrayOfDecks {


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

        salamanceDeck.addCards(arrCard)

        arrayListOfDecks.add(salamanceDeck)

        print(arrCard.toString())

        return arrayListOfDecks

    }
}