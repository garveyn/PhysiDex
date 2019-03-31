package com.physidex.physidex.database.daos

import androidx.room.*
import com.physidex.physidex.database.entities.*

@Dao
interface MyBinderDao {

    @Insert
    fun addCard(card: FullPokeCard)

    @Delete
    fun removeCard(card: FullPokeCard)

    @Query("SELECT * FROM PokeCardEntity")
    fun getCards(): List<PokeCardEntity>

    // use the Transaction annotation to make sure that the results are consistent,
    // especially since this call is using a relation.
    @Transaction
    @Query("SELECT * FROM PokeCardEntity")
    fun getFullCards(): List<FullPokeCard>

    // @Query("")
}