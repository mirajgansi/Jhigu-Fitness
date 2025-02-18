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
import com.example.jhigu_fitness.databinding.ActivityAddWorkoutBinding
import com.example.jhigu_fitness.model.WorkoutModel
import com.example.jhigu_fitness.repository.WorkoutRepositoryImp
import com.example.jhigu_fitness.utils.ImageUtlis
import com.example.jhigu_fitness.utils.LoadingUtils
import com.example.jhigu_fitness.viewmodel.WorkoutViewModel
import com.squareup.picasso.Picasso

class AddWorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWorkoutBinding
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var loadingUtils: LoadingUtils
    private lateinit var imageUtils: ImageUtlis
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUtils = ImageUtlis(this)
        loadingUtils = LoadingUtils(this)
        val repo = WorkoutRepositoryImp()
        workoutViewModel = WorkoutViewModel(repo)

        setupImageUpload()
        setupAddProductButton()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupImageUpload() {
        imageUtils.registerActivity { url ->
            url?.let {
                imageUri = it
                Picasso.get().load(it).into(binding.imageBrowse)
            }
        }

        binding.imageBrowse.setOnClickListener {
            imageUtils.launchGallery(this)
        }
    }

    private fun setupAddProductButton() {
        binding.btnAddProduct.setOnClickListener {
            uploadImage()
        }
    }

    private fun uploadImage() {
        loadingUtils.show()
        imageUri?.let { uri ->
            workoutViewModel.uploadImage(this, uri) { imageUrl ->
                Log.d("checkpoints", imageUrl.toString())
                if (imageUrl != null) {
                    addProduct(imageUrl)
                } else {
                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                    loadingUtils.dismiss()
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
            }
        } ?: run {
            loadingUtils.dismiss()
            Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addProduct(url: String) {
        val productName = binding.editProductName.text.toString()
        val productPrice = binding.editProductPrice.text.toString().toIntOrNull() ?: 0
        val productDesc = binding.editProductDesc.text.toString()

        if (productName.isNotBlank() && productPrice > 0 && productDesc.isNotBlank()) {
            val model = WorkoutModel(
                 "",
                productName = productName,
                productDesc = productDesc,
                price = productPrice,
                imageUrl = url
            )

            workoutViewModel.addWorkout(model) { success, message ->
                if (success) {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
                loadingUtils.dismiss()
            }
        } else {
            Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_LONG).show()
            loadingUtils.dismiss()
        }
    }
}