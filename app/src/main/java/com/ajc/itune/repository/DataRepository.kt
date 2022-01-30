package com.ajc.itune.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ajc.itune.local.Dao
import com.ajc.itune.local.MusicEntity
import com.ajc.itune.remote.api.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.ajc.itune.remote.Response

class DataRepository(private val userApi: ApiClient, private val dao: Dao) {
    private val userLiveData = MutableLiveData<Response>()

    val user: LiveData<Response>
        get() = userLiveData

    suspend fun getData(actor: String) {

        val result = userApi.getData(actor)
        if (result.body() != null) {
            userLiveData.postValue(result.body())
        }
    }

    fun insertDataInDb(itunesDbTable: MusicEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteAllDataFromDB()
            dao.addDataFromAPI(itunesDbTable)
        }
    }


    fun getDataFromDb(): LiveData<List<MusicEntity>> {
        return dao.getResponseFromDb()
    }

    fun deleteDataFromDb() {
        dao.deleteAllDataFromDB()
    }

}