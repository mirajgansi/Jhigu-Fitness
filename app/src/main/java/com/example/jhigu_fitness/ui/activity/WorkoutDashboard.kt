package com.example.jhigu_fitness.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.adapter.ExerciseAdapter
import com.example.jhigu_fitness.databinding.ActivityWorkoutDasboardBinding
import com.example.jhigu_fitness.repository.ExerciseRepository
import com.example.jhigu_fitness.repository.ExerciseRepositoryImp
import com.example.jhigu_fitness.viewmodel.ExerciseViewModel
import com.example.jhigu_fitness.viewmodel.ExerciseViewModelFactory

class WorkoutDashboard : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutDasboardBinding
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var adapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWorkoutDasboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = ExerciseRepositoryImp() // Your repository instance
        val factory = ExerciseViewModelFactory(repo) // Create the factory
        exerciseViewModel = ViewModelProvider(this, factory).get(ExerciseViewModel::class.java) // Use the factory to get the ViewModel

        adapter = ExerciseAdapter(this@WorkoutDashboard, ArrayList())

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Observe product list
        exerciseViewModel.allExercise.observe(this) { products ->
            products?.let { adapter.updateData(it) }
        }

        // Observe loading state
        exerciseViewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Floating button to add workouts
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, AddExerciseActivity::class.java))
        }

        // Swipe to delete feature
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // Disables moving items
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val productId = adapter.getExerciseId(viewHolder.adapterPosition)

                exerciseViewModel.deleteExercise(productId) { success, message ->
                    Toast.makeText(this@WorkoutDashboard, message, Toast.LENGTH_SHORT).show()
                }
            }
        }).attachToRecyclerView(binding.recyclerView)

        // Handle system bar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}


