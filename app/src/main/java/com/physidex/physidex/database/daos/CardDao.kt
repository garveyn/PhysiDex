package com.physidex.physidex.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.physidex.physidex.database.entities.*

@Dao
abstract class CardDao {

    fun addCard(card: FullPokeCard) {
        insertCard(card.pokeCard)
        insertAttacks(card.attacks)
    }

    @Insert
    abstract fun insertAttacks(attacks: List<PokeAttackEntity>)

    @Insert
    abstract fun insertCard(card: PokeCardEntity)

    // Update query- custom query because the only field changed is the number of copies.
    @Query("UPDATE Poke_Card SET num_copies = :numberOfNewCopies WHERE id == :cardId")
    abstract fun updateCard(cardId: String, numberOfNewCopies: Int)

    @Query("DELETE FROM Poke_Card WHERE id == :cardId")
    abstract fun deleteCard(cardId: String)

    @Query("DELETE FROM Poke_Card")
    abstract fun deleteAll()

    @Query("SELECT * FROM Poke_Card")
    abstract fun getCards(): List<PokeCardEntity>

    // use the Transaction annotation to make sure that the results are consistent,
    // especially since this call is using a relation.
    @Transaction
    @Query("SELECT * FROM Poke_Card ORDER BY national_pokedex_number DESC, supertype DESC")
    abstract fun getFullCards(): LiveData<List<FullPokeCard>>

    @Transaction
    @Query("SELECT * FROM Poke_Card WHERE id == :id")
    abstract fun getCard(id: String): LiveData<FullPokeCard>

    @Transaction
    @Query("SELECT * FROM Poke_Card LIMIT 1")
    abstract fun getOneCard(): LiveData<FullPokeCard>


    @Transaction
    @Query("SELECT * FROM Poke_Card ORDER BY first_added DESC")
    abstract fun getCardsByDate(): LiveData<List<FullPokeCard>>

    // not working correctly currently
    @Transaction
    @Query("SELECT *, COUNT(id) AS numCount FROM Poke_Card " +
            "LEFT JOIN Poke_Card_Per_Deck ON Poke_Card.id=Poke_Card_Per_Deck.card_id " +
            "ORDER BY numCount ASC")
    abstract fun getCardsByMostUsed(): LiveData<List<FullPokeCard>>

    @Transaction
    @Query("SELECT * FROM Poke_Card WHERE supertype == 'TRAINER'")
    abstract fun getTrainerCards(): LiveData<List<FullPokeCard>>

    data class CopiesPerId(var id: String, var numCopies: Int)

    @Query("SELECT id, num_copies AS numCopies FROM Poke_Card")
    abstract fun getAllIds(): LiveData<List<CopiesPerId>>

    @Transaction
    @Query("SELECT * FROM Poke_Card WHERE card_name LIKE :search")
    abstract fun searchCardName(search: String): List<FullPokeCard>

}