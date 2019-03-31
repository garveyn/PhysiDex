package com.physidex.physidex.database.daos

import android.arch.lifecycle.LiveData
import androidx.room.*
//import android.arch.persistence.room.*
import com.physidex.physidex.database.entities.*

@Dao
interface PokeCardDao {

    @Insert
    fun addCard(card: CardWithAttacks)

    @Delete
    fun removeCard(card: CardWithAttacks)

    @Query("SELECT * FROM PokeCardEntity")
    fun getCards(): List<PokeCardEntity>

    // use the Transaction annotation to make sure that the results are consistent,
    // especially since this call is using a relation.
    @Transaction
    @Query("SELECT * FROM PokeCardEntity")
    fun getFullCards(): LiveData<List<CardWithAttacks>>

    // @Query("")
}