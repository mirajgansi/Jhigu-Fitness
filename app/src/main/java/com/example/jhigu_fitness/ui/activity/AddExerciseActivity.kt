package com.example.jhigu_fitness.ui.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.ActivityAddExerciseBinding
import com.example.jhigu_fitness.model.ExerciseModel
import com.example.jhigu_fitness.repository.ExerciseRepositoryImp
import com.example.jhigu_fitness.utils.ImageUtlis
import com.example.jhigu_fitness.utils.LoadingUtils
import com.example.jhigu_fitness.viewmodel.ExerciseViewModel
import com.example.jhigu_fitness.viewmodel.ProductViewModel
import com.squareup.picasso.Picasso

class AddExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddExerciseBinding
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var loadingUtils: LoadingUtils
    private lateinit var imageUtils: ImageUtlis
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUtils = ImageUtlis(this)
        loadingUtils = LoadingUtils(this)

        val repo = ExerciseRepositoryImp()
        exerciseViewModel =ExerciseViewModel(repo)
        imageUtils.registerActivity { url ->
            url?.let {
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
        var productName = binding.editExerciseName.text.toString()
        var productPrice = binding.editExercisesets.text.toString().toInt()
        var productDesc = binding.editExerciseDesc.text.toString()

        val model = ExerciseModel(
             "",
            exerciseName = productName,
            description = productDesc,
            sets = productPrice,
            imageUrl = url
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