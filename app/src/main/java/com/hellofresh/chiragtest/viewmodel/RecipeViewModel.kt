package com.hellofresh.chiragtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hellofresh.chiragtest.database.RecipeTable
import com.hellofresh.chiragtest.database.Repository
import com.hellofresh.chiragtest.model.RecipeData
import com.hellofresh.chiragtest.network.CallResponseStatus
import com.hellofresh.chiragtest.network.NetworkManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*

class RecipeViewModel : ViewModel() {
    val recipeMutableLiveData = MutableLiveData<CallResponseStatus<List<RecipeData>>>()
    val favMutableLiveData = MutableLiveData<CallResponseStatus<List<RecipeTable>>>()
    var mainActivityJob :Job?= null
    var repository:Repository?=null

    init {
        mainActivityJob= Job()
        repository= Repository()
    }

    fun getRecipeList() {
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            recipeMutableLiveData.value = CallResponseStatus.error(exception)

        }
        val coroutineScope = CoroutineScope(mainActivityJob as CompletableJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            NetworkManager().getRepositories().let {
            recipeMutableLiveData.value = CallResponseStatus.success(it)
        }
        }
    }
    fun insertFavData(dataBaseDto: RecipeTable){
        val mainActivityJob= Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            favMutableLiveData.value = CallResponseStatus.error(exception)

        }
        val coroutineScope = CoroutineScope(mainActivityJob as CompletableJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {
            repository?.insert(dataBaseDto).let {
            }
        }

    }


    public override fun onCleared() {
        mainActivityJob?.cancel()
        super.onCleared()
    }
}