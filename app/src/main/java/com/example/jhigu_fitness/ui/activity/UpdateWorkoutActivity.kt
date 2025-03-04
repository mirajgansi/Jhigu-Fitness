package com.example.jhigu_fitness.ui.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.ActivityUpdateExerciseBinding
import com.example.jhigu_fitness.repository.WorkoutRepositoryImp
import com.example.jhigu_fitness.utils.ImageUtlis
import com.example.jhigu_fitness.viewmodel.WorkoutViewModel
import com.squareup.picasso.Picasso

class UpdateWorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateExerciseBinding
    private lateinit var workoutViewModel: WorkoutViewModel
    private var imageUri: Uri? = null
    private lateinit var imageUtils: ImageUtlis
    private lateinit var workoutId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Utilities and ViewModel
        imageUtils = ImageUtlis(this)
        val repo = WorkoutRepositoryImp()
        workoutViewModel = WorkoutViewModel(repo)

        // Register Image Picker
        imageUtils.registerActivity { uri ->
            uri?.let {
                imageUri = it
                Picasso.get().load(it).into(binding.imageBrowse)
            } ?: run {
                Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show()
            }
        }

        // Get Workout ID from Intent
        workoutId = intent.getStringExtra("workoutId").orEmpty()
        if (workoutId.isEmpty()) {
            Toast.makeText(this, "No workout ID provided", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Fetch Workout Details
        workoutViewModel.getWorkoutById(workoutId) { workout, success, message ->
            if (success) {
                workout?.let {
                    binding.updateName.setText(it.name)
                    binding.updateReps.setText(it.reps.toString())
                    binding.updateDesc.setText(it.description)
                    Picasso.get().load(it.imageUrl).placeholder(R.drawable.placeholder).into(binding.imageBrowse)
                }
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        // Click Listener for Update Button
        binding.updateExercise.setOnClickListener {
            if (validateInputs()) {
                if (imageUri != null) {
                    uploadImage()  // Upload new image first
                } else {
                    updateWorkout(null)  // Update without changing image
                }
            }
        }

        // Handle Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Image Browse Click Listener (to pick a new image)
        binding.imageBrowse.setOnClickListener {
            imageUtils.launchGallery(this)
        }
    }

    // Validate Input Fields
    private fun validateInputs(): Boolean {
        val name = binding.updateName.text.toString().trim()
        val reps = binding.updateReps.text.toString().trim()
        val desc = binding.updateDesc.text.toString().trim()

        return when {
            name.isEmpty() -> {
                binding.updateName.error = "Name is required"
                false
            }
            reps.isEmpty() || reps.toIntOrNull() == null -> {
                binding.updateReps.error = "Valid reps are required"
                false
            }
            desc.isEmpty() -> {
                binding.updateDesc.error = "Description is required"
                false
            }
            else -> true
        }
    }

    // Upload Image and Update Workout
    private fun uploadImage() {
        imageUri?.let { uri ->
            binding.updateExercise.isEnabled = false // Disable button during upload
            workoutViewModel.uploadImage(this, uri) { imageUrl ->
                binding.updateExercise.isEnabled = true
                if (imageUrl != null) {
                    Log.d("ImageUpload", "Image URL: $imageUrl")
                    updateWorkout(imageUrl)
                } else {
                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
            }
        } ?: run {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    // Update Workout Details
    private fun updateWorkout(imageUrl: String?) {
        val name = binding.updateName.text.toString().trim()
        val reps = binding.updateReps.text.toString().toInt()
        val desc = binding.updateDesc.text.toString().trim()

        val updatedData = mutableMapOf<String, Any>(
            "name" to name,
            "reps" to reps,
            "description" to desc
        )

        imageUrl?.let {
            updatedData["imageUrl"] = it
        }

        workoutViewModel.updateWork(workoutId, updatedData) { success, message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            if (success) finish()
        }
    }
}