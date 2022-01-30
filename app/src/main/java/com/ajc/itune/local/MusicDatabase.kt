package com.ajc.itune.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MusicEntity::class], version = 1)

abstract class Musicdatabase : RoomDatabase() {
    abstract fun getMusic(): Dao

    companion object {
        private var instance: Musicdatabase? = null
        fun getMusicDatabase(context: Context): Musicdatabase {
            return if (instance != null) {
                instance!!
            } else {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    Musicdatabase::class.java,
                    "music_db"
                )
                builder.fallbackToDestructiveMigration()
                instance = builder.build()
                instance!!
            }
        }
    }
}
/*
if (instance != null) {
    return instance!!
} else {
    val builder = Room.databaseBuilder(
        context.applicationContext,
        Musicdatabase::class.java,
        "music_db"
    )
    builder.fallbackToDestructiveMigration()
    instance = builder.build()
    return instance!!
}
 */
