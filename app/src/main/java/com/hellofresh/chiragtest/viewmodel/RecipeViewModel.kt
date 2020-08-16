package com.hellofresh.chiragtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hellofresh.chiragtest.model.RecipeData
import com.hellofresh.chiragtest.network.CallResponseStatus
import com.hellofresh.chiragtest.network.NetworkManager
import kotlinx.coroutines.*

class RecipeViewModel : ViewModel() {
    val recipeMutableLiveData = MutableLiveData<CallResponseStatus<List<RecipeData>>>()
    var mainActivityJob :Job?= null

    fun getRecipeList() {
        mainActivityJob= Job()
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

    public override fun onCleared() {
        mainActivityJob?.cancel()
        super.onCleared()
    }
}