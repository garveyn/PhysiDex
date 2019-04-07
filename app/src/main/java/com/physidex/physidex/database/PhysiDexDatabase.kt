package com.physidex.physidex.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.physidex.physidex.database.daos.PokeCardDao
import com.physidex.physidex.database.entities.FullPokeCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [FullPokeCard::class], version = 1)
abstract class PhysiDexDatabase : RoomDatabase() {

    abstract fun pokeCardDao(): PokeCardDao

    companion object {
        @Volatile
        private var INSTANCE: PhysiDexDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PhysiDexDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PhysiDexDatabase::class.java,
                        "PhysiDexDatabase"
                )
                        .addCallback(PhysiDexDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                return instance
            }
        }

        private class PhysiDexDatabaseCallback(private val scope: CoroutineScope) :
                RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.pokeCardDao())
                    }
                }
            }
        }

        fun populateDatabase(cardDao: PokeCardDao) {
            //TODO: get data???
        }
    }
}