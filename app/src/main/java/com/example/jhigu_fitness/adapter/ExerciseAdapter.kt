package com.example.jhigu_fitness.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.databinding.SampleWorkoutDetailBinding
import com.example.jhigu_fitness.model.ExerciseModel
import com.example.jhigu_fitness.ui.activity.WorkOutDetailActivity
import com.example.jhigu_fitness.ui.activity.WorkoutDashboard
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class ExerciseAdapter(
    private val context: Context,
    private var data: ArrayList<ExerciseModel>
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardWorkout)
        val imageView: ImageView = itemView.findViewById(R.id.getImage)
        val loading: ProgressBar = itemView.findViewById(R.id.progressBar2)
        var btnEdit: TextView = itemView.findViewById(R.id.btnExerciseEdit)
        var exerciseName: TextView = itemView.findViewById(R.id.displayExercisename)
        var exerciseReps: TextView = itemView.findViewById(R.id.displaySets)
        var exerciseDesc: TextView = itemView.findViewById(R.id.displayExerciseDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(
            R.layout.sample_exercise, parent, false
        )
        return ExerciseViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = data[position]

        holder.exerciseName.text = exercise.exerciseName
        holder.exerciseReps.text = data[position].sets.toString()
        holder.exerciseDesc.text = exercise.description

        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, WorkoutDashboard::class.java)
            intent.putExtra("exercise_id", exercise.exerciseId)
            context.startActivity(intent)
        }

        holder.cardView.setOnClickListener {
            val intent = Intent(context, WorkOutDetailActivity::class.java)
            intent.putExtra("exercise_id", exercise.exerciseId)
            context.startActivity(intent)
        }

        Picasso.get().load(exercise.imageUrl).into(holder.imageView, object : Callback {
            override fun onSuccess() {
                holder.loading.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
            }
        })
    }

    fun updateData(newExercises: List<ExerciseModel>) {
        data.clear()
        data.addAll(newExercises)
        notifyDataSetChanged()
    }

    fun getExerciseId(position: Int): String {
        return data[position].exerciseId
    }
}
