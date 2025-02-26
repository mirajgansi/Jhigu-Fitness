package com.example.jhigu_fitness.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jhigu_fitness.adapter.ExerciseAdapter
import com.example.jhigu_fitness.databinding.ActivityWorkoutDasboardBinding
import com.example.jhigu_fitness.repository.ExerciseRepositoryImp
import com.example.jhigu_fitness.repository.WorkoutRepositoryImp
import com.example.jhigu_fitness.viewmodel.ExerciseViewModel
import com.example.jhigu_fitness.viewmodel.WorkoutViewModel


class WorkoutDashboard : AppCompatActivity() {
    lateinit var binding: ActivityWorkoutDasboardBinding

    lateinit var productViewModel: WorkoutViewModel
    private lateinit var exerciseViewModel: ExerciseViewModel
    lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWorkoutDasboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exerciseAdapter = ExerciseAdapter(
            this@WorkoutDashboard,
            ArrayList()
        )

        val repo = WorkoutRepositoryImp()
        productViewModel = WorkoutViewModel(repo)

        val exerciseRepo = ExerciseRepositoryImp()
        exerciseViewModel = ExerciseViewModel(exerciseRepo)


        val productId = intent.getStringExtra("id") ?: ""


        Log.d("I am here", productId)
        exerciseViewModel.getExeriseFromDatabase(productId)

        exerciseViewModel.loadingState.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        // Floating button to add workouts
        binding.floatingActionButton.setOnClickListener {
            var intent = Intent(
                this@WorkoutDashboard,
                AddExerciseActivity::class.java
            )
            startActivity(intent)
        }


        exerciseViewModel.allExercise.observe(this) { product ->
            product?.let {
                exerciseAdapter.updateData(it) // Wrap it inside a list
            }



            binding.recyclerView.adapter = exerciseAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)

        }

//            ItemTouchHelper(object :
//                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder
//                ): Boolean {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    var productId = exerciseAdapter.getExerciseId(viewHolder.adapterPosition)
//
//                    exerciseViewModel.deleteExercise(productId) { success, message ->
//                        if (success) {
//                            Toast.makeText(
//                                this@WorkoutDashboard,
//                                message, Toast.LENGTH_SHORT
//                            ).show()
//                        } else {
//                            Toast.makeText(this@WorkoutDashboard, message, Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    }
//                }
//
//            }).attachToRecyclerView(binding.recyclerView)
//
//

//            binding.floatingActionButton.setOnClickListener {
//                var intent = Intent(
//                    this@WorkoutDashboard,
//                    AddExerciseActivity::class.java
//                )
//                startActivity(intent)
//            }
//            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//                insets
//            }
//        }
    }
}