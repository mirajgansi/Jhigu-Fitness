//package com.example.jhigu_fitness.ui.activity
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.jhigu_fitness.R
//import com.example.jhigu_fitness.adapter.ProductAdapter
//import com.example.jhigu_fitness.databinding.ActivityWorkoutDasboardBinding
//import com.example.jhigu_fitness.repository.ProductRepositoryImp
//import com.example.jhigu_fitness.viewmodel.ProductViewModel
//
//class WorkoutDashboard : AppCompatActivity() {
//    private lateinit var binding: ActivityWorkoutDasboardBinding
//    private lateinit var productViewModel: ProductViewModel
//    private lateinit var adapter: ProductAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        binding = ActivityWorkoutDasboardBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val repo = ProductRepositoryImp()
//        productViewModel = ProductViewModel(repo)
//        adapter = ProductAdapter(this@WorkoutDashboard, ArrayList())
//
//        // Setup RecyclerView
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = adapter
//
//        // Observe product list
//        productViewModel.getAllWorkout.observe(this) { products ->
//            products?.let { adapter.updateData(it) }
//        }
//
//        // Observe loading state
//        productViewModel.loading.observe(this) { isLoading ->
//            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//        }
//
//        // Floating button to add workouts
//        binding.floatingActionButton.setOnClickListener {
//            startActivity(Intent(this, AddWorkoutActivity::class.java))
//        }
//
//        // Swipe to delete feature
//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false // Disables moving items
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val productId = adapter.getProductId(viewHolder.adapterPosition)
//
//                productViewModel.deleteWorkout(productId) { success, message ->
//                    Toast.makeText(this@WorkoutDashboard, message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }).attachToRecyclerView(binding.recyclerView)
//
//        // Handle system bar insets
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}
