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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.adapter.ExerciseAdapter
import com.example.jhigu_fitness.databinding.ActivityWorkOutDetailBinding
import com.example.jhigu_fitness.repository.ExerciseRepositoryImp
import com.example.jhigu_fitness.viewmodel.ExerciseViewModel
import com.squareup.picasso.Picasso

class WorkOutDetailActivity : AppCompatActivity() {
        lateinit var binding: ActivityWorkOutDetailBinding

        lateinit var exerciseViewModel: ExerciseViewModel

        lateinit var exerciseAdapter: ExerciseAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            binding = ActivityWorkOutDetailBinding.inflate(layoutInflater)
            setContentView(binding.root)

            exerciseAdapter = ExerciseAdapter(this@WorkOutDetailActivity,
                ArrayList())

            val repo = ExerciseRepositoryImp()
            exerciseViewModel = ExerciseViewModel(repo)

            exerciseViewModel.getExerciseById()

            exerciseViewModel.exercise.observe(this){product->
                product?.let {
                    exerciseAdapter.updateData(it)
                }
            }

            exerciseViewModel.loadingState.observe(this){loading->
                if(loading){
                    binding.progressBar2.visibility = View.VISIBLE
                }else{
                    binding.progressBar2.visibility = View.GONE
                }
            }

            ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
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
                            Toast.makeText(this@WorkOutDetailActivity,
                                message,Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this@WorkOutDetailActivity,message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            })



            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }