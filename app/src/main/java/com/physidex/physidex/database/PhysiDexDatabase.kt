package com.physidex.physidex.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.physidex.physidex.database.daos.CardDao
import com.physidex.physidex.database.daos.DeckDao
import com.physidex.physidex.database.entities.PokeAttackEntity
import com.physidex.physidex.database.entities.PokeCardEntity
import com.physidex.physidex.database.entities.PokeCardPerDeckEntity
import com.physidex.physidex.database.entities.PokeDeckInfoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [
    PokeCardEntity::class, PokeAttackEntity::class,
    PokeDeckInfoEntity::class, PokeCardPerDeckEntity::class
], version = 4)
abstract class PhysiDexDatabase : RoomDatabase() {

    abstract fun fullCardDao(): CardDao
    abstract fun deckDao(): DeckDao

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
                        .fallbackToDestructiveMigration()
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
                        populateDatabase(database.fullCardDao())
                    }
                }
            }
        }

        fun populateDatabase(cardDao: CardDao) {
            //TODO: get data???
            //cardDao.deleteAll()
        }
    }
}