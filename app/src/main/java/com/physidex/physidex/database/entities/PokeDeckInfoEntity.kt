package com.physidex.physidex.database.entities

import androidx.room.*

@Entity
class PokeDeckInfoEntity constructor() {

    @PrimaryKey(autoGenerate = true) var id: Int = 0
    //@ColumnInfo(name = "deck_name") var deckName: String
}