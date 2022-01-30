package com.ajc.itune.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajc.itune.local.MusicEntity
import com.ajc.itune.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.ajc.itune.remote.Response


class MainViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun createTransaction(string: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.getData(string)
        }
    }

    val user: LiveData<Response>
        get() = dataRepository.user
    fun insertDataInDb(itunesDbTable: MusicEntity) {
        dataRepository.insertDataInDb(itunesDbTable)
    }

    fun deleteDataFromDb() {
        dataRepository.deleteDataFromDb()
    }

    fun getDataFromDb() {
        dataRepository.getDataFromDb()
    }
    fun getData(): LiveData<List<MusicEntity>> {
        return dataRepository.getDataFromDb()
    }
}
