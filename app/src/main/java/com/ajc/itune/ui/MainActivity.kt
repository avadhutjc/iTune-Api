package com.ajc.itune.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajc.itune.R
import com.ajc.itune.local.Dao
import com.ajc.itune.local.MusicEntity
import com.ajc.itune.local.Musicdatabase
import com.ajc.itune.remote.api.ApiClient
import com.ajc.itune.remote.api.Network
import com.ajc.itune.repository.DataRepository
import com.ajc.itune.ui.adapter.Adapter
import com.ajc.itune.ui.adapter.OnClick
import com.ajc.itune.viewmodel.MainViewModel
import com.ajc.itune.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.ajc.itune.remote.Result

class MainActivity : AppCompatActivity(), OnClick {
    private lateinit var adapterjoin: Adapter
    private lateinit var newViewModel: MainViewModel
    private lateinit var repository: DataRepository
    private var list = mutableListOf<Result>()
    private var musicList = mutableListOf<MusicEntity>()
    private lateinit var dao: Dao

    private var manager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = LinearLayoutManager(this)
        dao = Musicdatabase.getMusicDatabase(this).getMusic()

        val userApi = Network.getInstance().create(ApiClient::class.java)
        repository = DataRepository(userApi, dao)
        val factory = ViewModelFactory(repository)
        newViewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        etEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                etEditText.clearFocus()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadApi(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        setRecycle()
        newViewModel.getData().observe(this, Observer {
            musicList.clear()
            musicList.addAll(it)
        })
    }

    private fun insertDataToDb(resultModels: List<Result>) {
        newViewModel.deleteDataFromDb()
        resultModels.forEach {
            val itunesDb = MusicEntity(it.artistName, it.artworkUrl100, it.wrapperType, it.country)
            newViewModel.insertDataInDb(itunesDb)
        }
    }

    fun loadApi(query: String) {
        newViewModel.createTransaction(query)
        newViewModel.user.observe(this, Observer {
            list.clear()
            if (it != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    insertDataToDb(it.results)
                }
            }
            list.addAll(it.results as MutableList<Result>)
        })
    }

    private fun setRecycle() {
        adapterjoin = Adapter(musicList, this, this)
        recycle.adapter = adapterjoin
        recycle.layoutManager = GridLayoutManager(this, 2)
    }

    override fun setOnClick(result: Result) {

    }
}