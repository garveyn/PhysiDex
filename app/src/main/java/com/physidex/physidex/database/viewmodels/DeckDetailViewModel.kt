package com.physidex.physidex.database.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.physidex.physidex.database.PhysiDexDatabase
import com.physidex.physidex.database.daos.DeckDao
import com.physidex.physidex.database.entities.FullPokeCard
import com.physidex.physidex.database.entities.PokeDeckInfoEntity
import com.physidex.physidex.database.repositories.PokeDeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeckDetailViewModel(application: Application, deckId: Int): CardViewModel(application) {

    private val repository: PokeDeckRepository
    var deckInfo: LiveData<PokeDeckInfoEntity>
    var deckCards: LiveData<List<FullPokeCard>>
    var deckCardCopies: LiveData<List<DeckDao.CardWithNumCopies>>
    var numPokemon: LiveData<Int>
    var numTrainers: LiveData<Int>
    var numEnergy: LiveData<Int>

    init {
        val deckDao = PhysiDexDatabase.getDatabase(application, scope).deckDao()
        repository = PokeDeckRepository(deckDao)
        deckInfo = repository.deckDetail
        getDeck(deckId)
        deckCards = Transformations.switchMap(deckInfo) {
            deck -> repository.getCards(deck.id)
        }
        deckCardCopies = Transformations.switchMap(deckInfo) {
            deck -> repository.getCardCopies(deck.id)
        }

        numPokemon = Transformations.switchMap(deckInfo) {
            deck -> repository.getCardStat(deck.id, "POKEMON")
        }
        numTrainers = Transformations.switchMap(deckInfo) {
            deck -> repository.getCardStat(deck.id, "TRAINER")
        }
        numEnergy = Transformations.switchMap(deckInfo) {
            deck -> repository.getCardStat(deck.id, "ENERGY")
        }
    }

    fun getDeck(deckId: Int) = scope.launch(Dispatchers.IO) {
        deckInfo = repository.getDeck(deckId)
    }

    /**
     * Update Deck info
     * The only deck fields that can be edited by the user are the name and required size.
     * @param deck the deck should be edited before this function,
     * so that the UI can reflect the changes. The database then will be update to match.
     *
     */
    fun updateDeck(deck: PokeDeckInfoEntity) = scope.launch(Dispatchers.IO) {
        repository.updateDeck(deck.id, deck.deckName, deck.requiredSize)
    }
}