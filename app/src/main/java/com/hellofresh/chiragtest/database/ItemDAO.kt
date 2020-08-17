package com.hellofresh.chiragtest.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ItemDAO {

    @Query("SELECT * FROM recipe_table")
     fun getList():LiveData<List<RecipeTable>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insert(vararg items: RecipeTable)

    @Query("DELETE FROM recipe_table")
    suspend fun deleteAll()
}
