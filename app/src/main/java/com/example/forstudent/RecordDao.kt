package com.example.forstudent

import androidx.room.Update
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDao {

    @Insert
    suspend fun insert(record: Record)

    @Query("SELECT * FROM records")
    fun getAllRecords(): LiveData<List<Record>>

    @Query("DELETE FROM records")
    suspend fun deleteAll()

    @Update
    suspend fun update(record: Record)
}
