package com.unlimit.chiragtest.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ItemDAO {

    @Query("SELECT * FROM joke_table")
    fun getJokeList(): LiveData<List<JokesTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg items: JokesTable)

}
