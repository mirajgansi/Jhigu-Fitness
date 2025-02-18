package com.example.jhigu_fitness.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.ActivityUpdateWorkoutBinding
import com.example.jhigu_fitness.repository.WorkoutRepository
import com.example.jhigu_fitness.repository.WorkoutRepositoryImp
import com.example.jhigu_fitness.viewmodel.WorkoutViewModel

class UpdateWorkoutActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateWorkoutBinding

    lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var repo = WorkoutRepositoryImp()
        workoutViewModel = WorkoutViewModel(repo)

//        var products : ProductModel? =
//                        intent.getParcelableExtra("products")

        var productId: String = intent.getStringExtra("products")
            .toString()

        workoutViewModel.getWorkoutById(productId)

        workoutViewModel.products.observe(this) {
            binding.updateName.setText(it?.productName.toString())
            binding.updateDesc.setText(it?.productDesc.toString())
            binding.updatePrice.setText(it?.price.toString())
        }
        binding.updateAddProduct.setOnClickListener {
            var name = binding.updateName.text.toString()
            var price = binding.updatePrice.text.toString().toInt()
            var desc = binding.updateDesc.text.toString()

            var updatedData = mutableMapOf<String, Any>()

            updatedData["productName"] = name
            updatedData["productDesc"] = desc
            updatedData["price"] = price

            workoutViewModel.updateWork(productId, updatedData) { success, message ->
                if (success) {
                    Toast.makeText(this@UpdateWorkoutActivity, message, Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this@UpdateWorkoutActivity, message, Toast.LENGTH_LONG).show()

                }
            }
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
