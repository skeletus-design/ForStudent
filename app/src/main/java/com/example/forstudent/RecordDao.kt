package com.example.forstudent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDao {

    @Insert
    suspend fun insert(record: Record)

    @Query("SELECT * FROM records")
    suspend fun getAllRecords(): List<Record>

    @Query("DELETE FROM records")
    suspend fun deleteAll()
}