package com.physidex.physidex.database.daos

import androidx.room.*
import com.physidex.physidex.database.entities.*

@Dao
interface PokeCardDao {

    @Query("SELECT * FROM PokeCard")
    fun getCards(): List<PokeCard>

    // use the Transaction annotation to make sure that the results are consistent,
    // especially since this call is using a relation.
    @Transaction
    @Query("SELECT * FROM PokeCard")
    fun getFullCards(): List<CardWithAttacks>
}