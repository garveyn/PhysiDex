package com.physidex.physidex.database

import androidx.room.*
import com.physidex.physidex.database.daos.MyBinderDao
import com.physidex.physidex.database.entities.CardWithAttacks

@Database(entities = [CardWithAttacks::class], version = 1)
abstract class PhysiDexDatabase : RoomDatabase() {

    abstract fun myBinderDao(): MyBinderDao
}