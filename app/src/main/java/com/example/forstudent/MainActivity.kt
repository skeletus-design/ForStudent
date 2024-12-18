package com.example.forstudent

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val recordViewModel: RecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleInput = findViewById<EditText>(R.id.title_input)
        val descriptionInput = findViewById<EditText>(R.id.description_input)
        val addButton = findViewById<Button>(R.id.add_button)
        val recordsView = findViewById<TextView>(R.id.records_view)

        // Наблюдаем за всеми записями
        recordViewModel.allRecords.observe(this, Observer { records ->
            updateRecordsView(recordsView, records)
        })

        addButton.setOnClickListener {
            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                val newRecord = Record(title = title, description = description)
                recordViewModel.insert(newRecord)
                titleInput.text.clear()
                descriptionInput.text.clear()
            }
        }
    }

    private fun updateRecordsView(recordsView: TextView, records: List<Record>) {
        val text = records.joinToString("\n") { record ->
            "${record.id}. ${record.title} - ${record.description}"
        }
        recordsView.text = text

        // Добавим обработчик кликов для редактирования записи
        recordsView.setOnClickListener {
            val selectedRecord = getRecordFromView(recordsView)
            if (selectedRecord != null) {
                showEditDialog(selectedRecord)
            }
        }
    }

    private fun getRecordFromView(recordsView: TextView): Record? {
        val selectedRecordText = recordsView.text.split("\n").firstOrNull()
        val parts = selectedRecordText?.split(". ") ?: return null
        val id = parts[0].toIntOrNull() ?: return null
        val titleDescription = parts[1].split(" - ")
        val title = titleDescription[0]
        val description = titleDescription[1]
        return Record(id = id, title = title, description = description)
    }

    private fun showEditDialog(record: Record) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Edit Record")

        val inputTitle = EditText(this)
        inputTitle.setText(record.title)
        builder.setView(inputTitle)

        val inputDescription = EditText(this)
        inputDescription.setText(record.description)
        builder.setView(inputDescription)

        builder.setPositiveButton("Save") { _, _ ->
            val updatedTitle = inputTitle.text.toString()
            val updatedDescription = inputDescription.text.toString()

            if (updatedTitle.isNotEmpty() && updatedDescription.isNotEmpty()) {
                val updatedRecord = record.copy(title = updatedTitle, description = updatedDescription)
                recordViewModel.update(updatedRecord)
            }
        }
        builder.setNegativeButton("Cancel", null)

        builder.show()
    }
}
