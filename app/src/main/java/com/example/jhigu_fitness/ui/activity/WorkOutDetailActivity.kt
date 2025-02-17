package com.example.jhigu_fitness.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.ActivityWorkOutDetailBinding
import com.example.jhigu_fitness.repository.ProductRepositoryImp
import com.example.jhigu_fitness.viewmodel.ProductViewModel

class WorkOutDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkOutDetailBinding
    private lateinit var productViewModel: ProductViewModel
    private var getWorkoutById: Int = -1  // ID to fetch the correct workout

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        binding = ActivityWorkOutDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Initialize ViewModel
//        val repo = ProductRepositoryImp()
//        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
//
//        // Get Workout ID from Intent
//        getWorkoutById = intent.getIntExtra("WORKOUT_ID", -1)
//
//        if (getWorkoutById == -1) {
//            Toast.makeText(this, "Invalid Workout ID", Toast.LENGTH_SHORT).show()
//            finish()
//            return
//        }
//
//        // Fetch workout details
//        productViewModel.getWorkoutById(getWorkoutById)
//
//        // Observe the workout details
//        productViewModel.selectedWorkout.observe(this) { workout ->
//            if (workout != null) {
//                binding.textViewWorkoutName.text = workout.name
//                binding.textViewWorkoutDescription.text = workout.description
//                binding.textViewWorkoutExercises.text = workout.exercises.joinToString("\n")
//            } else {
//                Toast.makeText(this, "Workout not found", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        // Observe loading state
//        productViewModel.loading.observe(this) { isLoading ->
//            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//        }
//
//        // Handle system bar insets
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

