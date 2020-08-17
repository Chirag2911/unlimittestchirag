package com.hellofresh.chiragtest.database
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey


@Entity(tableName = "recipe_table")
data class RecipeTable(
    @PrimaryKey
    @ColumnInfo
    val id:String,
    @ColumnInfo
    var isFavourite: Boolean)

