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
import com.example.jhigu_fitness.databinding.ActivityAddExerciseBinding
import com.example.jhigu_fitness.model.ExerciseModel
import com.example.jhigu_fitness.repository.ExerciseRepositoryImp
import com.example.jhigu_fitness.utils.ImageUtlis
import com.example.jhigu_fitness.utils.LoadingUtlis
import com.example.jhigu_fitness.viewmodel.ExerciseViewModel
import com.squareup.picasso.Picasso

class AddExerciseActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddExerciseBinding

    lateinit var exerciseViewModel: ExerciseViewModel
    lateinit var loadingUtils: LoadingUtlis

    lateinit var imageUtils: ImageUtlis
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUtils = ImageUtlis(this)

        loadingUtils = LoadingUtlis(this)
        val repo = ExerciseRepositoryImp()
        exerciseViewModel = ExerciseViewModel(repo)

        imageUtils.registerActivity { url ->
            url.let {
                imageUri = it
                Picasso.get().load(it).into(binding.imageBrowseExercise)
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
                Log.d("checkpoints", imageUrl.toString())
                if (imageUrl != null) {
                    addProduct(imageUrl)
                } else {
                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                }
            }
        }
    }

    private fun addProduct(url: String) {
        var exerciseName = binding.editExerciseName.text.toString()
        var exerciseSets = binding.editExercisesets.text.toString().toInt()
        var exerciseDesc = binding.editExerciseDesc.text.toString()

        var model = ExerciseModel(
            "",
            exerciseName,
            exerciseDesc, exerciseSets, url
        )

        exerciseViewModel.addExercise(model) { success, message ->
            if (success) {
                Toast.makeText(
                    this@AddExerciseActivity,
                    message, Toast.LENGTH_LONG
                ).show()
                finish()
                loadingUtils.dismiss()
            } else {
                Toast.makeText(
                    this@AddExerciseActivity,
                    message, Toast.LENGTH_LONG
                ).show()
                loadingUtils.dismiss()
            }
        }
    }
}
