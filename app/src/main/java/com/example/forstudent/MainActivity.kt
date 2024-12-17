package com.example.forstudent

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.forstudent.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = MainDb.getDb(this)
        db.getDao().getAllRecords().asLiveData().observe(this){list->
            binding.recordsView.text = ""
            list.forEach{
                val text = "id: ${it.id} Заголовок: ${it.title} Описание: ${it.description}\n"
                binding.recordsView.append(text)
            }
        }

        binding.addButton.setOnClickListener {
            val record = Record(
                null,
                binding.titleInput.text.toString(),
                binding.descriptionInput.text.toString()
            )

            Thread{
                db.getDao().insertRecord(record)
            }.start()
        }
    }
}