package com.example.jhigu_fitness.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.jhigu_fitness.adapter.ProductAdapter
import com.example.jhigu_fitness.databinding.ActivityWorkoutDasboardBinding
import com.example.jhigu_fitness.databinding.FragmentWorkoutDashboardBinding
import com.example.jhigu_fitness.model.ExerciseModel
import com.example.jhigu_fitness.repository.ExerciseRepositoryImp
import com.example.jhigu_fitness.repository.ProductRepositoryImp
import com.example.jhigu_fitness.viewmodel.ExerciseViewModel


class WorkoutDashboard : AppCompatActivity() {
    lateinit var binding: ActivityWorkoutDasboardBinding

    lateinit var exerciseViewModel: ExerciseViewModel

    lateinit var exerciseAdapter: ExerciseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWorkoutDasboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        exerciseAdapter = ExerciseAdapter(this@WorkoutDashboard,
            ArrayList())

        val repo = ExerciseRepositoryImp()
        exerciseViewModel = ExerciseViewModel(repo)

        exerciseViewModel.fetchAllExercises()

        exerciseViewModel.allExercise.observe(this){product->
            product?.let {
                exerciseAdapter.updateData(it)
            }
        }

        exerciseViewModel.loadingState.observe(this){loading->
            if(loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

        binding.recyclerView.adapter = exerciseAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var productId = exerciseAdapter.getExerciseId(viewHolder.adapterPosition)

                exerciseViewModel.deleteExercise(productId){
                        success,message->
                    if(success){
                        Toast.makeText(this@WorkoutDashboard,
                            message,Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@WorkoutDashboard,message,Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }).attachToRecyclerView(binding.recyclerView)



        binding.floatingActionButton.setOnClickListener {
            var intent = Intent(this@WorkoutDashboard,
                AddExerciseActivity::class.java
            )
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}