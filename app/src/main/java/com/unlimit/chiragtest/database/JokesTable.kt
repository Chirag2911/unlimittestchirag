package com.unlimit.chiragtest.database
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "joke_table")
data class JokesTable(
    @PrimaryKey
    @ColumnInfo
    val joke: String
)
