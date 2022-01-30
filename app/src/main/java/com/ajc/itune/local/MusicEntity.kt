package com.ajc.itune.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "iTune_db")
data class MusicEntity(
    @ColumnInfo(name = "artistName") val artistName: String,
    @ColumnInfo(name = "artistImageUrl") val artistImageUrl: String,
    @ColumnInfo(name = "wrapperType") val wrapperType: String,
    @ColumnInfo(name = "country") val country: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}
