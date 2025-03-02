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
import com.example.jhigu_fitness.repository.ExerciseRepositoryImp
import com.example.jhigu_fitness.utils.ImageUtlis
import com.example.jhigu_fitness.utils.LoadingUtils
import com.example.jhigu_fitness.viewmodel.ExerciseViewModel
import com.squareup.picasso.Picasso

class UpdateExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateExerciseBinding
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var imageUtils: ImageUtlis
    private lateinit var loadingUtils: LoadingUtils
    private var imageUri: Uri? = null
    private lateinit var exerciseId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Utilities and ViewModel
        imageUtils = ImageUtlis(this)
        loadingUtils = LoadingUtils(this)
        val repo = ExerciseRepositoryImp()
        exerciseViewModel = ExerciseViewModel(repo)

        // Register Image Picker
        imageUtils.registerActivity { uri ->
            uri?.let {
                imageUri = it
                Picasso.get().load(it).into(binding.imageBrowse)
            } ?: run {
                Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show()
            }
        }

        // Get Exercise ID from Intent
        exerciseId = intent.getStringExtra("exerciseId").orEmpty()  // Changed key to avoid confusion
        if (exerciseId.isEmpty()) {
            Toast.makeText(this, "No exercise ID provided", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Fetch Exercise Details
        exerciseViewModel.getExerciseById(exerciseId)

        // Observe Exercise Details (assuming LiveData named 'exercise')
        exerciseViewModel.exercise.observe(this) { exercise ->
            exercise?.let {
                binding.updateName.setText(it.exerciseName)
                binding.updateDesc.setText(it.description)
                binding.updateReps.setText(it.sets.toString())
                Picasso.get().load(it.imageUrl).placeholder(R.drawable.placeholder)
                    .into(binding.imageBrowse)
            } ?: run {
                Toast.makeText(this, "Failed to load exercise details", Toast.LENGTH_SHORT).show()
            }
        }

        // Image Browse Click Listener
        binding.imageBrowse.setOnClickListener {
            imageUtils.launchGallery(this)
        }

        // Update Button Click Listener
        binding.updateExercise.setOnClickListener {
            if (validateInputs()) {
                if (imageUri != null) {
                    uploadImage()
                } else {
                    updateExercise(null)
                }
            }
        }

        // Handle Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Validate Input Fields
    private fun validateInputs(): Boolean {
        val name = binding.updateName.text.toString().trim()
        val sets = binding.updateReps.text.toString().trim()
        val desc = binding.updateDesc.text.toString().trim()

        return when {
            name.isEmpty() -> {
                binding.updateName.error = "Exercise name is required"
                false
            }
            sets.isEmpty() || sets.toIntOrNull() == null || sets.toInt() <= 0 -> {
                binding.updateReps.error = "Valid number of sets is required"
                false
            }
            desc.isEmpty() -> {
                binding.updateDesc.error = "Description is required"
                false
            }
            else -> true
        }
    }

    // Upload Image and Update Exercise
    private fun uploadImage() {
        loadingUtils.show()
        imageUri?.let { uri ->
            exerciseViewModel.uploadImage(this, uri) { imageUrl ->
                if (imageUrl != null) {
                    Log.d("ImageUpload", "Image URL: $imageUrl")
                    updateExercise(imageUrl)
                } else {
                    Log.e("Upload Error", "Failed to upload image")
                    loadingUtils.dismiss()
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
            }
        } ?: run {
            loadingUtils.dismiss()
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            updateExercise(null)
        }
    }

    // Update Exercise Details
    private fun updateExercise(imageUrl: String?) {
        val name = binding.updateName.text.toString().trim()
        val sets = binding.updateReps.text.toString().toInt()
        val desc = binding.updateDesc.text.toString().trim()

        val updatedData = mutableMapOf<String, Any>(
            "exerciseName" to name,
            "description" to desc,
            "sets" to sets
        )

        imageUrl?.let {
            updatedData["imageUrl"] = it
        }

        exerciseViewModel.updateExercise(exerciseId, updatedData) { success, message ->
            loadingUtils.dismiss()
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            if (success) finish()
        }
    }
}