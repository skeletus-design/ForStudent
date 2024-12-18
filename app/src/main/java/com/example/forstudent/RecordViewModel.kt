package com.example.forstudent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecordViewModel(application: Application) : AndroidViewModel(application) {

    private val recordRepository: RecordRepository
    val allRecords: LiveData<List<Record>>

    init {
        val recordDao = AppDatabase.getDatabase(application).recordDao()
        recordRepository = RecordRepository(recordDao)
        allRecords = recordRepository.allRecords
    }

    fun insert(record: Record) {
        viewModelScope.launch {
            recordRepository.insert(record)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            recordRepository.deleteAll()
        }
    }
}
