package com.unlimit.chiragtest.database

import androidx.lifecycle.LiveData
import com.unlimit.chiragtest.App


class Repository() {

    private var itemDAO: ItemDAO? = null
    var allJokeRepos: LiveData<List<JokesTable>>? = null

    init {
        val db = AppDatabase.getDatabase(App.context)
        db?.let {
            itemDAO = db.itemDAO()
            allJokeRepos = itemDAO?.getJokeList();
        }
    }

    suspend fun insert(item: JokesTable) {
        itemDAO?.insert(item)
    }


}
