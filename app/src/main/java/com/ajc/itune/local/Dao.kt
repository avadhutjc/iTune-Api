package com.ajc.itune.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDataFromAPI(result: MusicEntity)

    @Query("select * from iTune_db")
    fun getResponseFromDb(): LiveData<List<MusicEntity>>

    @Query("delete from iTune_db")
    fun deleteAllDataFromDB()
}