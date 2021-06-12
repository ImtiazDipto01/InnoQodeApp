package com.imtiaz.innoqodetest.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.imtiaz.innoqodetest.data.local.dao.PostDao
import com.imtiaz.innoqodetest.data.local.entity.BlogPost


@Database(entities = [BlogPost::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    companion object {

        private var appDb: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : AppDatabase {
            if(appDb == null) {
                appDb = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "appDb.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    })
                    .build()
            }
            return appDb!!
        }
    }

    abstract fun postDao(): PostDao

}