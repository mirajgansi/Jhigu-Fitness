package com.example.jhigu_fitness.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.jhigu_fitness.databinding.ActivityWorkOutDetailBinding
import com.squareup.picasso.Picasso

class WorkOutDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkOutDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize binding first
        binding = ActivityWorkOutDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the exercise details from the Intent
        val exerciseId = intent.getStringExtra("EXERCISE_ID")
        val name = intent.getStringExtra("EXERCISE_NAME")
        val sets = intent.getStringExtra("EXERCISE_REPS")?.toIntOrNull() ?: 0  // Convert to int, default to 0 if null
        val desc = intent.getStringExtra("EXERCISE_DESC")
        val image = intent.getStringExtra("EXERCISE_IMAGE")

        // Check if the exercise ID is null or empty
        if (exerciseId.isNullOrEmpty()) {
            Toast.makeText(this, "Exercise ID is missing", Toast.LENGTH_SHORT).show()
            finish()  // Close activity if ID is not found
            return
        }

        // Debugging logs
        Log.d(
            "WorkOutDetailActivity",
            "Exercise ID: $exerciseId, Name: $name, Sets: $sets, Desc: $desc, Image: $image"
        )

        // Set data to UI
        binding.displayExercisename.text = name
        binding.displayExerciseSets.text = "Sets: $sets"  // Display Sets

        if (!image.isNullOrEmpty()) {
            Picasso.get().load(image).into(binding.imageBrowseExercise)  // Load image with Picasso
        }

        // Set up the button listener AFTER retrieving intent values
        binding.startButton.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("EXERCISE_ID", exerciseId)
                putExtra("EXERCISE_NAME", name)
                putExtra("EXERCISE_REPS", sets.toString())  // Convert back to String if needed
                putExtra("EXERCISE_DESC", desc)
                putExtra("EXERCISE_IMAGE", image)
            }
            setResult(RESULT_OK, resultIntent)
            finish()  // Returns to WorkoutDashboard
        }
    }
}
