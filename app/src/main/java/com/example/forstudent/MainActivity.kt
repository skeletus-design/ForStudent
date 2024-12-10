package com.example.forstudent

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Получаем ссылки на элементы интерфейса
        val inputField: EditText = findViewById(R.id.inputField)
        val showButton: Button = findViewById(R.id.showButton)
        val displayText: TextView = findViewById(R.id.displayText)

        // Устанавливаем слушатель для кнопки
        showButton.setOnClickListener {
            val userInput = inputField.text.toString()
            if (userInput.isNotEmpty()) {
                displayText.text = userInput
            } else {
                displayText.text = "Пожалуйста, введите текст!"
            }
        }
    }
}
