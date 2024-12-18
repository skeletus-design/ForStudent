package com.example.forstudent

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var database: RecordDatabase
    private lateinit var dao: RecordDao
    private lateinit var recordsView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация базы данных
        database = RecordDatabase.getDatabase(this)
        dao = database.recordDao()

        // Получаем ссылки на UI элементы
        val titleInput = findViewById<EditText>(R.id.title_input)
        val descriptionInput = findViewById<EditText>(R.id.description_input)
        val addButton = findViewById<Button>(R.id.add_button)
        recordsView = findViewById(R.id.records_view)

        // Отображаем записи при запуске
        loadRecords()

        addButton.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val record = Record(title = title, description = description)
                addRecord(record)
                titleInput.text.clear()
                descriptionInput.text.clear()
            }
        }
    }

    private fun addRecord(record: Record) {
        lifecycleScope.launch {
            dao.insert(record)
            loadRecords()
        }
    }

    private fun loadRecords() {
        lifecycleScope.launch {
            val records = dao.getAllRecords()
            val text = records.joinToString("\n") { "${it.id}. ${it.title} - ${it.description}" }
            recordsView.text = text
        }
    }
}