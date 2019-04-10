package com.physidex.physidex.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.physidex.physidex.PokeCard
import com.physidex.physidex.database.entities.*

@Dao
abstract class FullCardDao {

    fun addCard(card: FullPokeCard) {
        insertCard(card.pokeCard)
        insertAttacks(card.attacks)
    }

    @Insert
    abstract fun insertAttacks(attacks: List<PokeAttackEntity>)

    @Insert
    abstract fun insertCard(card: PokeCardEntity)

    // Update query- custom query because the only field changed is the number of copies.
    @Query("UPDATE PokeCardEntity SET num_copies = :numberOfNewCopies WHERE id = :cardId")
    abstract fun updateCard(cardId: String, numberOfNewCopies: Int)

//    @Delete
//    abstract fun removeCard(card: FullPokeCard)

    @Query("DELETE FROM PokeCardEntity")
    abstract fun deleteAll()

    @Query("SELECT * FROM PokeCardEntity")
    abstract fun getCards(): List<PokeCardEntity>

    // use the Transaction annotation to make sure that the results are consistent,
    // especially since this call is using a relation.
    @Transaction
    @Query("SELECT * FROM PokeCardEntity")
    abstract fun getFullCards(): LiveData<List<FullPokeCard>>

    @Transaction
    @Query("SELECT * FROM PokeCardEntity WHERE id == :id")
    abstract fun getCard(id: String): List<FullPokeCard>

    @Transaction
    @Query("SELECT * FROM PokeCardEntity ORDER BY first_added ASC")
    abstract fun getCardsByDate(): LiveData<List<FullPokeCard>>

    data class CopiesPerId(var id: String, var numCopies: Int)

    @Query("SELECT id, num_copies AS numCopies FROM PokeCardEntity")
    abstract fun getAllIds(): LiveData<List<CopiesPerId>>

}