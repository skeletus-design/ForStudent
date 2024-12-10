package com.example.forstudent

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecordStorage(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("records", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Тип для десериализации списка
    private val type = object : TypeToken<List<Record>>() {}.type

    fun saveRecords(records: List<Record>) {
        val json = gson.toJson(records)
        sharedPreferences.edit().putString("record_list", json).apply()
    }

    fun loadRecords(): MutableList<Record> {
        val json = sharedPreferences.getString("record_list", null)
        return if (json != null) {
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }
}
