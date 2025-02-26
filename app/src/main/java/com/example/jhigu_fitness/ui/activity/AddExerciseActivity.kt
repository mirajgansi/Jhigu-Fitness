package com.example.jhigu_fitness.ui.activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.adapter.WorkoutAdapter
import com.example.jhigu_fitness.databinding.ActivityAddExerciseBinding
import com.example.jhigu_fitness.model.ExerciseModel
import com.example.jhigu_fitness.repository.ExerciseRepositoryImp
import com.example.jhigu_fitness.repository.WorkoutRepositoryImp
import com.example.jhigu_fitness.utils.ImageUtlis
import com.example.jhigu_fitness.utils.LoadingUtils
import com.example.jhigu_fitness.viewmodel.ExerciseViewModel
import com.example.jhigu_fitness.viewmodel.WorkoutViewModel
import com.squareup.picasso.Picasso

class AddExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddExerciseBinding
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var loadingUtils: LoadingUtils
    private lateinit var imageUtils: ImageUtlis
    private var imageUri: Uri? = null
    private var spinner: String? = null // To store the selected product ID
    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUtils = ImageUtlis(this)
        loadingUtils = LoadingUtils(this)

        // Direct instantiation of ViewModels without factory
        val repo = WorkoutRepositoryImp()
        val repository = ExerciseRepositoryImp()
        workoutViewModel = WorkoutViewModel(repo)
        exerciseViewModel = ExerciseViewModel(repository)

        imageUtils.registerActivity { url ->
            url?.let {
                imageUri = it
                Picasso.get().load(it).into(binding.imageBrowseExercise)
            }
        }
         var workoutIds: List<String> = emptyList()
        // Fetch product IDs and set up the spinner
        workoutViewModel.getAllWorkout() // Call this to fetch data
        workoutViewModel.allWorkout.observe(this) { workouts ->
             workoutIds = workouts?.map { it.productId } ?: emptyList()
            val workoutName= workouts?.map { it.productName } ?: emptyList()
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, workoutName)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }

        // Set up spinner selection listener
        binding.spinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                // Store the product ID based on the selected position
                spinner = workoutIds[position]
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {
                spinner = null
            }
        }

        binding.imageBrowseExercise.setOnClickListener {
            imageUtils.launchGallery(this)
        }

        binding.btnAddPExercise.setOnClickListener {
            uploadImage()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun uploadImage() {
        loadingUtils.show()
        imageUri?.let { uri ->
            exerciseViewModel.uploadImage(this, uri) { imageUrl ->
                Log.d("ImageUpload", imageUrl.toString())
                if (imageUrl != null) {
                    addExercise(imageUrl)
                } else {
                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                    loadingUtils.dismiss()
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addExercise(url: String) {
        val exerciseName = binding.editExerciseName.text.toString()
        val exercisesets = binding.editExercisesets.text.toString().toIntOrNull() ?: 0
        val productDesc = binding.editExerciseDesc.text.toString()
        val productname = spinner

        if (exerciseName.isNotBlank() && exercisesets > 0 && productDesc.isNotBlank() && productname != null) {
            // Only one of these can be null, but we require all except 'productname' to be non-null or non-blank
            val model = ExerciseModel(
                "",
                productName = productname,
                exerciseName = exerciseName,
                description = productDesc,
                sets = exercisesets,
                imageUrl = url
            )

            exerciseViewModel.addExercise(model) { success, message ->
                if (success) {
                    Toast.makeText(this@AddExerciseActivity, message, Toast.LENGTH_LONG).show()
                    finish()
                    loadingUtils.dismiss()
                } else {
                    Toast.makeText(this@AddExerciseActivity, message, Toast.LENGTH_LONG).show()
                    loadingUtils.dismiss()
                }
            }
        } else {
            Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_LONG).show()
            loadingUtils.dismiss()
        }
    }
}