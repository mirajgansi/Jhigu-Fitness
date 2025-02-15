package com.example.jhigu_fitness.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.viewmodel.ExerciseViewModel
import com.squareup.picasso.Picasso

class WorkOutDetailActivity : AppCompatActivity() {

    private lateinit var exerciseNameText: TextView
    private lateinit var exerciseSetsText: TextView
    private lateinit var exerciseImage: ImageView
    private lateinit var startButton: Button
    private lateinit var exerciseViewModel: ExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_out_detail)

        // Initialize views
        exerciseNameText = findViewById(R.id.ExerciseName)
        exerciseSetsText = findViewById(R.id.Exercisesets)
        exerciseImage = findViewById(R.id.imageBrowseExercise)
        startButton = findViewById(R.id.startButton)

        // Get the exerciseId passed from the previous activity
        val exerciseId = intent.getIntExtra("exercise_id", -1)
        if (exerciseId != -1) {
            // Initialize ViewModel using ViewModelProvider first
            exerciseViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)

            // Fetch exercise data from database by ID
            exerciseViewModel.getExerciseById(exerciseId.toString())

            // Observe changes to exercise data
            exerciseViewModel.exercise.observe(this, Observer { exercise ->
                exercise?.let {
                    // Update UI with the fetched exercise data
                    exerciseNameText.text = exercise.exerciseName
                    exerciseSetsText.text = "Sets: ${exercise.sets}"
                    Picasso.get().load(exercise.imageUrl).into(exerciseImage)
                }
            })
        } else {
            Toast.makeText(this, "Exercise not found!", Toast.LENGTH_SHORT).show()
        }
    }
}
