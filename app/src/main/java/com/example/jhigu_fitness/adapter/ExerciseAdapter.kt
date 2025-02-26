package com.example.jhigu_fitness.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.model.ExerciseModel
import com.example.jhigu_fitness.ui.activity.UpdateExerciseActivity
import com.example.jhigu_fitness.ui.activity.WorkOutDetailActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class ExerciseAdapter(
    private val context: Context,
    private var data: ArrayList<ExerciseModel>
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.CardExercise)
        val imageView: ImageView = itemView.findViewById(R.id.getImageexe)
        val loading: ProgressBar = itemView.findViewById(R.id.ExeProgressbar)
        val btnEdit: TextView = itemView.findViewById(R.id.btnExerciseEdit)
        val exerciseName: TextView = itemView.findViewById(R.id.displayExercisename)
        val exerciseReps: TextView = itemView.findViewById(R.id.displayExerciseSets)
        val exerciseDesc: TextView = itemView.findViewById(R.id.displayExerciseDesc)

        init {
            Log.d(
                "ExerciseViewHolder",
                "CardView: $cardView, ImageView: $imageView, ProgressBar: $loading"
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sample_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = data[position]

        // Handle nullability with defaults
        holder.exerciseName.text = exercise.exerciseName ?: "Unnamed Exercise"
        holder.exerciseReps.text = exercise.sets?.toString() ?: "0"
        holder.exerciseDesc.text = exercise.description ?: "No description"

        // Edit button launches UpdateExerciseActivity
        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, UpdateExerciseActivity::class.java).apply {
                putExtra("exerciseId", exercise.exerciseId)  // Matches UpdateExerciseActivity
            }
            context.startActivity(intent)
        }

        // Card click launches WorkOutDetailActivity
        holder.cardView.setOnClickListener {
            val intent = Intent(context, WorkOutDetailActivity::class.java).apply {
                putExtra("EXERCISE_ID", exercise.exerciseId)
                putExtra("EXERCISE_NAME", exercise.exerciseName)
                putExtra("EXERCISE_REPS", exercise.sets?.toString())
                putExtra("EXERCISE_DESC", exercise.description)
                putExtra("EXERCISE_IMAGE", exercise.imageUrl)
            }
            context.startActivity(intent)
        }

        // Load image with Picasso
        holder.loading.visibility = View.VISIBLE
        Picasso.get().load(exercise.imageUrl).into(holder.imageView, object : Callback {
            override fun onSuccess() {
                holder.loading.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
                Log.e("Picasso", "Error loading image: ${e?.message}")
                holder.loading.visibility = View.GONE
                holder.imageView.setImageResource(R.drawable.placeholder)
            }
        })
    }

    fun updateData(newExercises: List<ExerciseModel>) {
        Log.d("ExerciseAdapter", "Received ${newExercises.size} exercises for update")
        data.clear()
        data.addAll(newExercises)
        notifyDataSetChanged()
    }

    fun getExerciseId(position: Int): String {
        return data[position].exerciseId ?: ""
    }
}