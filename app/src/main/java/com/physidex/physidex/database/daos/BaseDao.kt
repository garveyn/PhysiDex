//package com.physidex.physidex.database.daos
//
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Update
//import com.physidex.physidex.database.entities.PokeAttackEntity
//import com.physidex.physidex.database.entities.PokeCardEntity
//
//// based on code by florina muntenescu
//// https://gist.github.com/florina-muntenescu/1c78858f286d196d545c038a71a3e864
//
//interface BaseDao<T> {
//
//    // insert one object into database
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(obj: T)
//
//    // insert an array of objects into the database
//    @Insert
//    fun insert(vararg obj: T)
//
//    // update an object from the database
//    @Update
//    fun update(obj: T)
//
//}
//
//abstract class PokeCardDao: BaseDao<PokeCardEntity>
//
//abstract class PokeAttackDao: BaseDao<PokeAttackEntity>