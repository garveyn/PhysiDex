package com.physidex.physidex.database.entities

import androidx.room.*
import java.util.*

@Entity(tableName = "Poke_Deck_Info")
class PokeDeckInfoEntity (

    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "deck_name") var deckName: String,
    @ColumnInfo(name = "required_size") var requiredSize: Int,
    @ColumnInfo(name = "last_modified") var lastModified: String,
    @ColumnInfo(name = "created") var created: String,
    @ColumnInfo(name = "games_played") var gamesPlayed: Int
)