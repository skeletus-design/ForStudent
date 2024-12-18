package com.example.forstudent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecordViewModel(application: Application) : AndroidViewModel(application) {

    private val recordRepository: RecordRepository = RecordRepository(AppDatabase.getDatabase(application).recordDao())
    val allRecords: LiveData<List<Record>> = recordRepository.allRecords

    fun insert(record: Record) {
        viewModelScope.launch {
            recordRepository.insert(record)
        }
    }

    fun update(record: Record) {
        viewModelScope.launch {
            recordRepository.update(record)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            recordRepository.deleteAll()
        }
    }
}

