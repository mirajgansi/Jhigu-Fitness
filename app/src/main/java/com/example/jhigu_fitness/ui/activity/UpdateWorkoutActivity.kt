package com.example.jhigu_fitness.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.ActivityUpdateWorkoutBinding
import com.example.jhigu_fitness.repository.ProductRepositoryImp
import com.example.jhigu_fitness.viewmodel.ProductViewModel

class UpdateWorkoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateWorkoutBinding

    lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = ProductRepositoryImp()
        productViewModel = ProductViewModel(repo)

//        var products : ProductModel? =
//                        intent.getParcelableExtra("products")

        var productId : String = intent.getStringExtra("products")
            .toString()

        productViewModel.getWorkoutById(productId)

        productViewModel.products.observe(this){
            binding.updateName.setText(it?.productName.toString())
            binding.updateDesc.setText(it?.productDesc.toString())
            binding.updatePrice.setText(it?.price.toString())
        }
//        products.let {
//            binding.updateName.setText(it?.productName.toString())
//            binding.updateDesc.setText(it?.productDesc.toString())
//            binding.updatePrice.setText(it?.price.toString())
//        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
