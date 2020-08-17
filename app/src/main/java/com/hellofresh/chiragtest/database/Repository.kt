package com.hellofresh.chiragtest.database

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.hellofresh.chiragtest.App
import io.reactivex.Flowable
import io.reactivex.Observable
import kotlinx.coroutines.flow.flatMapConcat

class Repository() {

    private var itemDAO: ItemDAO? = null
    public var allRepos: LiveData<List<RecipeTable>>? = null

    init {
        val db = AppDatabase.getDatabase(App.context)
        db?.let {
            itemDAO = db.itemDAO()
            allRepos = itemDAO?.getList();
        }
    }


    suspend fun insert(item: RecipeTable) {
        itemDAO?.insert(item)
    }


}
