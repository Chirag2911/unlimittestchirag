package com.unlimit.chiragtest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(JokesTable::class), version = 1)
abstract class AppDatabase : RoomDatabase() {


    abstract fun itemDAO(): ItemDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getDatabase(context: Context): AppDatabase? {
             val tempINSTANCE: AppDatabase?= INSTANCE
                if (tempINSTANCE!=null)
                    return tempINSTANCE
            synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "joke_database")
                            .build()
                    }
                return INSTANCE

            }
        }
    }

}
