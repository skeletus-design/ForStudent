package com.example.forstudent

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var storage: RecordStorage
    private var records = mutableListOf<Record>()
    private var nextId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация хранилища
        storage = RecordStorage(this)
        records = storage.loadRecords()
        if (records.isNotEmpty()) {
            nextId = records.maxOf { it.id } + 1
        }

        val titleInput = findViewById<EditText>(R.id.title_input)
        val descriptionInput = findViewById<EditText>(R.id.description_input)
        val addButton = findViewById<Button>(R.id.add_button)
        val recordsView = findViewById<TextView>(R.id.records_view)

        updateRecordsView(recordsView)

        addButton.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                addRecord(title, description)
                titleInput.text.clear()
                descriptionInput.text.clear()
                updateRecordsView(recordsView)
            }
        }
    }

    private fun addRecord(title: String, description: String) {
        val record = Record(nextId++, title, description)
        records.add(record)
        storage.saveRecords(records)
    }

    private fun updateRecordsView(recordsView: TextView) {
        val text = records.joinToString("\n") { record ->
            "${record.id}. ${record.title} - ${record.description}"
        }
        recordsView.text = text
    }
}
