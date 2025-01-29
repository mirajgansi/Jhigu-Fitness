package com.example.jhigu_fitness.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.jhigu_fitness.R

class QuestionPage : AppCompatActivity() {

    // Track selected options
    private val selectedOptions = mutableSetOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_question)

        // Find buttons by their IDs
        val buttonOption1 = findViewById<Button>(R.id.optionFeelConfident)
        val buttonOption2 = findViewById<Button>(R.id.optionImproveHealth)
        val buttonOption3 = findViewById<Button>(R.id.optionBoostEnergy)
        val buttonOption4 = findViewById<Button>(R.id.optionReleaseStress)
        val buttonNext = findViewById<Button>(R.id.buttonNext)

        // Set click listeners for the options
        val optionButtons = listOf(buttonOption1, buttonOption2, buttonOption3, buttonOption4)
        optionButtons.forEach { button ->
            button.setOnClickListener {
                handleOptionClick(button)
            }
        }

        // Set click listener for the "Next" button
        buttonNext.setOnClickListener {
            if (selectedOptions.isEmpty()) {
                Toast.makeText(this, "Please select at least one option", Toast.LENGTH_SHORT).show()
            } else {
                val selectedTexts = selectedOptions.joinToString(", ") { it.text.toString() }
                Toast.makeText(this, "Selected options: $selectedTexts", Toast.LENGTH_SHORT).show()
                // Add your submit logic here
            }
        }
    }

    // Handle option click for multiple selection
    private fun handleOptionClick(button: Button) {
        if (selectedOptions.contains(button)) {
            // If already selected, deselect it
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            selectedOptions.remove(button)
        } else {
            // Otherwise, select it
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            selectedOptions.add(button)
        }
    }
}
