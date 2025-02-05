package com.example.jhigu_fitness.ui.activity


import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.jhigu_fitness.R


class WeightActivity: AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var weightTens: EditText
    private lateinit var weightUnits: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)

        progressBar = findViewById(R.id.progressBar)
        weightTens = findViewById(R.id.weightTens)
        weightUnits = findViewById(R.id.weightUnits)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val backButton = findViewById<ImageView>(R.id.backArrow)

        // Set input filters for number input
        setupNumberInput(weightTens, 3) // Allow up to 3 digits (triple digits)
        setupNumberInput(weightUnits, 1) // Only single digit (0-9)

        nextButton.setOnClickListener {
            if (validateInput()) {
                animateProgressAndNavigate()
            }
        }

        backButton.setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // Add text validation
        weightTens.addTextChangedListener { validateNumbers(weightTens, 999) } // Max 999 kg
        weightUnits.addTextChangedListener { validateNumbers(weightUnits, 9) } // Max 9
    }

    private fun setupNumberInput(editText: EditText, maxLength: Int) {
        editText.filters = arrayOf(
            InputFilter.LengthFilter(maxLength),
            InputFilter { source, start, end, dest, dstart, dend ->
                if (source.isBlank()) return@InputFilter null
                source.toString().takeIf { it.matches(Regex("\\d+")) }
            }
        )
    }

    private fun validateNumbers(editText: EditText, maxValue: Int) {
        val text = editText.text.toString()
        if (text.isNotEmpty()) {
            val value = text.toInt()
            if (value > maxValue) {
                editText.setText(maxValue.toString())
                editText.setSelection(editText.text.length)
            }
        }
    }

    private fun validateInput(): Boolean {
        val tens = weightTens.text.toString()
        val units = weightUnits.text.toString()

        return when {
            tens.isEmpty() -> {
                Toast.makeText(this, "Please enter weight in kg", Toast.LENGTH_SHORT).show()
                false
            }
            tens.toInt() == 0 && units.toInt() == 0 -> {
                Toast.makeText(this, "Weight cannot be zero", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun animateProgressAndNavigate() {
        // Animate progress bar from 25% to 50%
        progressBar.progress = 50 // Direct update since we removed animation

        // Navigate immediately
        startActivity(Intent(this, MotivationActivity::class.java).apply {
            putExtra("WEIGHT", "${weightTens.text}.${weightUnits.text} kg")
        })
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}