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
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.Observer
//import androidx.recyclerview.widget.ItemTouchHelper
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.jhigu_fitness.R
//import com.example.jhigu_fitness.adapter.ExerciseAdapter
//import com.example.jhigu_fitness.databinding.ActivityWorkOutDetailBinding
//import com.example.jhigu_fitness.repository.ExerciseRepositoryImp
//import com.example.jhigu_fitness.viewmodel.ExerciseViewModel
//import com.google.android.play.core.integrity.v
//import com.squareup.picasso.Picasso
//
//class WorkOutDetailActivity : AppCompatActivity() {
//        lateinit var binding: ActivityWorkOutDetailBinding
//
//        lateinit var exerciseViewModel: ExerciseViewModel
//
//        lateinit var exerciseAdapter: ExerciseAdapter
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            enableEdgeToEdge()
//            binding = ActivityWorkOutDetailBinding.inflate(layoutInflater)
//            setContentView(binding.root)
//
//            exerciseAdapter = ExerciseAdapter(this@WorkOutDetailActivity,
//                ArrayList())
//
//            val repo = ExerciseRepositoryImp()
//            exerciseViewModel = ExerciseViewModel(repo)
//
//          val getExercisebyId= exerciseViewModel.getExerciseById()
//
//
//            getExercisebyId.let {
//                   exerciseViewModel.getExerciseById((it?.e.tostring()))
//                }
//            }
//
//
//
//
//            ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,
//                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder
//                ): Boolean {
//                    TODO("Not yet implemented")
//                }
//
//
//
//
//
//            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//                insets
//            }
//        }
//    }
//}