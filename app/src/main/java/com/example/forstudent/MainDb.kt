package com.example.forstudent

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Record::class], version = 1)
abstract class MainDb : RoomDatabase() {
    abstract fun getDao(): RecordDao
    companion object{
        fun getDb(context: Context): MainDb {
            return Room.databaseBuilder(context.applicationContext, MainDb::class.java, "notes.db").build()
        }
    }
}