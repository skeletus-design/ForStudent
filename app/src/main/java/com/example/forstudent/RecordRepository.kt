package com.example.forstudent

import androidx.lifecycle.LiveData

class RecordRepository(private val recordDao: RecordDao) {

    val allRecords: LiveData<List<Record>> = recordDao.getAllRecords()

    suspend fun insert(record: Record) {
        recordDao.insert(record)
    }

    suspend fun update(record: Record) {
        recordDao.update(record)
    }

    suspend fun deleteAll() {
        recordDao.deleteAll()
    }
}
