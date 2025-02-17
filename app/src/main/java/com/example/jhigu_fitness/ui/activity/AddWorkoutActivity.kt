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
import com.example.jhigu_fitness.model.ProductModel
import com.example.jhigu_fitness.repository.ProductRepositoryImp
import com.example.jhigu_fitness.utils.ImageUtlis
import com.example.jhigu_fitness.utils.LoadingUtlis
import com.example.jhigu_fitness.viewmodel.ProductViewModel
import com.squareup.picasso.Picasso

class AddWorkoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddWorkoutBinding

    lateinit var productViewModel: ProductViewModel

    lateinit var loadingUtils: LoadingUtlis

    lateinit var imageUtils: ImageUtlis

    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUtils = ImageUtlis(this)

        loadingUtils = LoadingUtlis(this)
        val repo = ProductRepositoryImp()
        productViewModel = ProductViewModel(repo)


        imageUtils.registerActivity { url ->
            url.let { it ->
                imageUri = it
                Picasso.get().load(it).into(binding.imageBrowse)
            }
        }
        binding.imageBrowse.setOnClickListener {
            imageUtils.launchGallery(this)
        }
        binding.btnAddProduct.setOnClickListener {
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
            productViewModel.uploadImage(this, uri) { imageUrl ->
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
        var productName = binding.editProductName.text.toString()
        var productPrice = binding.editProductPrice.text.toString().toInt()
        var productDesc = binding.editProductDesc.text.toString()

        var model = ProductModel(
            "",
            productName,
            productDesc, productPrice, url
        )

        productViewModel.addWorkout(model) { success, message ->
            if (success) {
                Toast.makeText(
                    this@AddWorkoutActivity,
                    message, Toast.LENGTH_LONG
                ).show()
                finish()
                loadingUtils.dismiss()
            } else {
                Toast.makeText(
                    this@AddWorkoutActivity,
                    message, Toast.LENGTH_LONG
                ).show()
                loadingUtils.dismiss()
            }
        }
    }
}