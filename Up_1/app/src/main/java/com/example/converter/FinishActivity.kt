package com.example.converter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        val editTextInput: EditText = findViewById(R.id.EditTextOne)
        val editTextFrom: EditText = findViewById(R.id.EditTextTwo)
        val editTextTo: EditText = findViewById(R.id.EditTextThree)
        val editTextResult: EditText = findViewById(R.id.EditTextFour)
        val buttonCalculate: Button = findViewById(R.id.Vichislit)
        val buttonHome: Button = findViewById(R.id.Home)

        val unitToBytes = mapOf(
            "bit" to (1.0 / 8),
            "byte" to 1.0,
            "kb" to 1024.0,
            "mb" to (1024.0 * 1024),
            "gb" to (1024.0 * 1024 * 1024),
            "tb" to (1024.0 * 1024 * 1024 * 1024)
        )

        buttonCalculate.setOnClickListener {
            val inputStr = editTextInput.text.toString().trim()
            val fromUnit = editTextFrom.text.toString().trim().lowercase()
            val toUnit = editTextTo.text.toString().trim().lowercase()

            if (inputStr.isEmpty() || fromUnit.isEmpty() || toUnit.isEmpty()) {
                Toast.makeText(this, "Заполните поля ввода и выбора данных", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val number = try {
                inputStr.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Ошибка! Неправильный ввод", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fromMult = unitToBytes[fromUnit]
            val toMult = unitToBytes[toUnit]

            if (fromMult == null) {
                Toast.makeText(this, "Неизвестная единица измерения: $fromUnit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (toMult == null) {
                Toast.makeText(this, "Неизвестная единица измерения: $toUnit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bytes = number * fromMult
            val result = bytes / toMult

            val resultStr = if (result == result.toLong().toDouble()) {
                result.toLong().toString()
            } else {
                "%.4f".format(result).replace(',', '.')
            }

            editTextResult.setText("$resultStr $toUnit")
        }

        buttonHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}