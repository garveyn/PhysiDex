package com.physidex.physidex.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
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

//    @Delete
//    abstract fun removeCard(card: FullPokeCard)

    @Query("SELECT * FROM PokeCardEntity")
    abstract fun getCards(): List<PokeCardEntity>

    // use the Transaction annotation to make sure that the results are consistent,
    // especially since this call is using a relation.
    @Transaction
    @Query("SELECT * FROM PokeCardEntity")
    abstract fun getFullCards(): LiveData<List<FullPokeCard>>

    @Query("SELECT * FROM PokeCardEntity WHERE id == :id")
    abstract fun getCard(id: String): List<FullPokeCard>
}