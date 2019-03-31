package com.physidex.physidex.database

import android.content.Context
import androidx.room.*
import com.physidex.physidex.database.daos.PokeCardDao
import com.physidex.physidex.database.entities.CardWithAttacks

@Database(entities = [CardWithAttacks::class], version = 1)
abstract class PhysiDexDatabase : RoomDatabase() {

    abstract fun pokeCardDao(): PokeCardDao

    companion object {
        @Volatile
        private var INSTANCE: PhysiDexDatabase? = null

        fun getDatabase(context: Context): PhysiDexDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PhysiDexDatabase::class.java,
                        "PhysiDexDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}