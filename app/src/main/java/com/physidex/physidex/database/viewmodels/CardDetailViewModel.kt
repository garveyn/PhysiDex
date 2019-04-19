package com.physidex.physidex.database.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.physidex.physidex.database.PhysiDexDatabase
import com.physidex.physidex.database.daos.CardDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.repositories.PokeCardRepository
import com.physidex.physidex.database.repositories.PokeDeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardDetailViewModel(application: Application): CardViewModel(application) {

    private val cardRepository: PokeCardRepository
    private val deckRepository: PokeDeckRepository
    val allCardIds: LiveData<List<CardDao.CopiesPerId>>

    init {
        val cardDao = PhysiDexDatabase.getDatabase(application, scope).fullCardDao()
        cardRepository = PokeCardRepository(cardDao)
        allCardIds = cardRepository.allCardIds
        val deckDao = PhysiDexDatabase.getDatabase(application, scope).deckDao()
        deckRepository = PokeDeckRepository(deckDao)
    }

    fun insert(card: FullPokeCard, previousNumberOfCopies: Int) = scope.launch(Dispatchers.IO) {

        if (previousNumberOfCopies == 0 ) {
            cardRepository.insert(card)
        } else {
            cardRepository.update(card.pokeCard.id, card.pokeCard.numCopies)
        }
    }

    fun removeOne(card: FullPokeCard) = scope.launch(Dispatchers.IO) {
        // check that there is more than one copy in My Binder
        if (card.pokeCard.numCopies > 1) {
            cardRepository.update(card.pokeCard.id, card.pokeCard.numCopies - 1)
        }
    }

    fun removeAll(card: FullPokeCard) = scope.launch(Dispatchers.IO) {
        // check that there is at least one copy in My Binder
        if (card.pokeCard.numCopies >= 1) {
            card.pokeCard.numCopies = 0
            cardRepository.removeAllCopies(card.pokeCard.id)
        }
    }

    fun addToDeck(deckId: Int, card: FullPokeCard) = scope.launch(Dispatchers.IO) {

        // determine if this is the first copy of this card being added to this deck
        if ((card.numCopiesPerDeck!! - 1) == 0) {
            // if so, add it in a new entry
            // card.numCopiesPerDeck = 1
            deckRepository.addCard(deckId, card.pokeCard.id)
        } else {
            // if not, just increase the number of copies
            // card.numCopiesPerDeck = card.numCopiesPerDeck!! + 1
            deckRepository.updateCard(deckId, card.pokeCard.id, card.numCopiesPerDeck!!)
        }
    }

    fun removeFromDeck(deckId: Int, card: FullPokeCard) = scope.launch(Dispatchers.IO) {
        if (card.numCopiesPerDeck != null && card.numCopiesPerDeck!! >= 0) {
            // card.numCopiesPerDeck = card.numCopiesPerDeck!! - 1
            if (card.numCopiesPerDeck!! == 0) {
                deckRepository.removeCard(deckId, card.pokeCard.id)
            } else {
                deckRepository.updateCard(deckId, card.pokeCard.id, card.numCopiesPerDeck!!)
            }
        }
    }

}