package com.unlimit.chiragtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unlimit.chiragtest.database.JokesTable
import com.unlimit.chiragtest.database.Repository
import com.unlimit.chiragtest.model.JokesData
import com.unlimit.chiragtest.network.CallResponseStatus
import com.unlimit.chiragtest.network.NetworkManager
import kotlinx.coroutines.*

class JokesViewModel : ViewModel() {
    private val jokesMutableLiveData = MutableLiveData<CallResponseStatus<JokesData>>()
    var repository: Repository? = null

    init {
        repository = Repository()
    }

    fun getJokesList() {
        val mainActivityJob = Job()
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            jokesMutableLiveData.value = CallResponseStatus.error(exception)

        }
        val coroutineScope = CoroutineScope(mainActivityJob as CompletableJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            NetworkManager().getJokes().let {
                jokesMutableLiveData.value = CallResponseStatus.success(it)
            }
        }
    }

    fun insertJokesData(dataBaseDto: JokesTable) {
        val mainActivityJob = Job()
        val errorHandler = CoroutineExceptionHandler { _, exception ->
        }
        val coroutineScope = CoroutineScope(mainActivityJob as CompletableJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            repository?.insert(dataBaseDto).let {
            }
        }

    }

    fun getJokeLiveData(): LiveData<CallResponseStatus<JokesData>> {
        return jokesMutableLiveData
    }
}