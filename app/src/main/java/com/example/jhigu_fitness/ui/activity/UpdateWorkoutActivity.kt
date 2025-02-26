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
import com.example.jhigu_fitness.databinding.ActivityUpdateWorkoutBinding
import com.example.jhigu_fitness.repository.WorkoutRepositoryImp
import com.example.jhigu_fitness.utils.ImageUtlis
import com.example.jhigu_fitness.viewmodel.WorkoutViewModel
import com.squareup.picasso.Picasso

class UpdateWorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateWorkoutBinding
    private lateinit var workoutViewModel: WorkoutViewModel
    private var imageUri: Uri? = null
    private lateinit var imageUtils: ImageUtlis  // Fixed typo
    private lateinit var productId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateWorkoutBinding.inflate(layoutInflater)
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

        // Get Product ID from Intent
        productId = intent.getStringExtra("products").orEmpty()
        if (productId.isEmpty()) {
            Toast.makeText(this, "No workout ID provided", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Fetch Product Details
        workoutViewModel.getWorkoutById(productId)

        // Observe Product Details
        workoutViewModel.products.observe(this) { product ->
            product?.let {
                binding.updateName.setText(it.productName)
                binding.updateDesc.setText(it.productDesc)
                binding.updatePrice.setText(it.price.toString())
                Picasso.get().load(it.imageUrl).placeholder(R.drawable.placeholder).into(binding.imageBrowse)
            } ?: run {
                Toast.makeText(this, "Failed to load workout details", Toast.LENGTH_SHORT).show()
            }
        }

        // Click Listener for Update Button
        binding.updateAddProduct.setOnClickListener {
            if (validateInputs()) {
                if (imageUri != null) {
                    uploadImage()  // Upload new image first
                } else {
                    updateProduct(null)  // Update without changing image
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
        val price = binding.updatePrice.text.toString().trim()
        val desc = binding.updateDesc.text.toString().trim()

        return when {
            name.isEmpty() -> {
                binding.updateName.error = "Name is required"
                false
            }
            price.isEmpty() || price.toIntOrNull() == null -> {
                binding.updatePrice.error = "Valid price is required"
                false
            }
            desc.isEmpty() -> {
                binding.updateDesc.error = "Description is required"
                false
            }
            else -> true
        }
    }

    // Upload Image and Update Product
    private fun uploadImage() {
        imageUri?.let { uri ->
            binding.updateAddProduct.isEnabled = false // Disable button during upload
            workoutViewModel.uploadImage(this, uri) { imageUrl ->
                binding.updateAddProduct.isEnabled = true
                if (imageUrl != null) {
                    Log.d("ImageUpload", "Image URL: $imageUrl")
                    updateProduct(imageUrl)
                } else {
                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
            }
        } ?: run {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    // Update Product Details
    private fun updateProduct(imageUrl: String?) {
        val name = binding.updateName.text.toString().trim()
        val price = binding.updatePrice.text.toString().toInt()
        val desc = binding.updateDesc.text.toString().trim()

        val updatedData = mutableMapOf<String, Any>(
            "productName" to name,
            "productDesc" to desc,
            "price" to price
        )

        imageUrl?.let {
            updatedData["imageUrl"] = it
        }

        workoutViewModel.updateWork(productId, updatedData) { success, message ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            if (success) finish()
        }
    }
}