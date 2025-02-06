package com.example.jhigu_fitness.ui.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.ActivityUpdateWorkoutBinding
import com.example.jhigu_fitness.repository.ProductRepositoryImp
import com.example.jhigu_fitness.utils.ImageUtlis
import com.example.jhigu_fitness.utils.LoadingUtlis
import com.example.jhigu_fitness.viewmodel.ProductViewModel
import com.squareup.picasso.Picasso

class UpdateWorkoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateWorkoutBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var loadingUtils: LoadingUtlis
    private lateinit var imageUtils: ImageUtlis
    private var imageUri: Uri? = null
    private var productId: String? = null  // Store product ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUtils = ImageUtlis(this)
        loadingUtils = LoadingUtlis(this)

        val repo = ProductRepositoryImp()
        productViewModel = ProductViewModel(repo)

        // Retrieve product ID from intent
        productId = intent.getStringExtra("products")?.toString()

        // Load product details
        productId?.let {
            productViewModel.getProductById(it)
        }

        // Observe product details and populate UI fields
        productViewModel.products.observe(this) { product ->
            product?.let {
                binding.updateName.setText(it.productName ?: "")
                binding.updateDesc.setText(it.productDesc ?: "")
                binding.updatePrice.setText(it.price?.toString() ?: "")

                // Load existing image if available
                it.imageUrl?.let { url ->
                    Picasso.get().load(url).into(binding.imageBrowse)
                }
            }
        }

        // Handle image selection
        imageUtils.registerActivity { url ->
            url?.let {
                imageUri = it
                Picasso.get().load(it).into(binding.imageBrowse)
            }
        }
        binding.imageBrowse.setOnClickListener {
            imageUtils.launchGallery(this)
        }

        // Handle update button click
        binding.updateAddProduct.setOnClickListener {
            uploadImage()
        }

        // Adjust window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun uploadImage() {
        loadingUtils.show()
        if (imageUri != null) {
            productViewModel.uploadImage(this, imageUri!!) { imageUrl ->
                if (imageUrl != null) {
                    updateProduct(imageUrl)
                } else {
                    loadingUtils.dismiss()
                    Log.e("Upload Error", "Failed to upload image to Cloudinary")
                }
            }
        } else {
            updateProduct(null) // Proceed with updating product without changing image
        }
    }

    private fun updateProduct(imageUrl: String?) {
        productId?.let { id ->
            val updatedData: MutableMap<String, Any> = mutableMapOf(
                "productName" to binding.updateName.text.toString(),
                "productDesc" to binding.updateDesc.text.toString(),
                "price" to (binding.updatePrice.text.toString().toDoubleOrNull() ?: 0.0)
            )

            // Add image URL only if a new image was selected
            if (imageUrl != null) {
                updatedData["imageUrl"] = imageUrl
            }

            // Call ViewModel function to update product
            productViewModel.updateProduct(id, updatedData) { success, message ->
                loadingUtils.dismiss()
                if (success) {
                    Log.d("Update", "Product updated successfully: $message")
                    finish()
                } else {
                    Log.e("Update", "Failed to update product: $message")
                }
            }

        }
    }
}
