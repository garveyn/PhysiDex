package com.physidex.physidex.database.entities

import androidx.room.*
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "Poke_Deck_Info")
class PokeDeckInfoEntity(deckName: String, requiredSize: Int, lastModified: String,
                         created: String, gamesPlayed: Int) {

    @PrimaryKey(autoGenerate = true) var id: Int = 0
    @ColumnInfo(name = "deck_name") var deckName: String = deckName
    @ColumnInfo(name = "required_size") var requiredSize: Int = requiredSize
    @ColumnInfo(name = "last_modified") var lastModified: String = lastModified
    @ColumnInfo(name = "created") var created: String = created
    @ColumnInfo(name = "games_played") var gamesPlayed: Int = gamesPlayed

    @Ignore
    constructor(deckName: String = "New Deck", requiredSize: Int = 60) :
            this(deckName, requiredSize, "", "", 0) {
        val currentDate = Calendar.getInstance().time
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val currentDateString = format.format(currentDate).toString()
        this.lastModified = currentDateString
        this.created = currentDateString
    }
}