package com.example.jhigufitness.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jhigufitness.R
import com.example.jhigufitness.databinding.ActivityAddProductBinding
import com.example.jhigufitness.model.ProductModel
import com.example.jhigufitness.repository.ProductRepository
import com.example.jhigufitness.repository.ProductRepositoryimpl
import com.example.jhigufitness.utils.LoadingUtils
import com.example.jhigufitness.viewmodel.ProductViewModel

class AddProductActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddProductBinding

    lateinit var productViewModel: ProductViewModel

    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)
        val  repo = ProductRepository()
        productViewModel = ProductViewModel(repo)

        binding.btnaddProduct.setOnClickListener{
            var name = binding.pName.text.toString()
            var price = binding.pPrice.text.toString().toInt()
            var desc = binding.pDesc.text.toString()

            var model= ProductModel("" , name,desc,price)

            pro

        }
        setContentView(R.layout.activity_add_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}