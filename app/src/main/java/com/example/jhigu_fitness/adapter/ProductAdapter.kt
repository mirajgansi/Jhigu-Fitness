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
import com.example.jhigu_fitness.model.ProductModel
import com.example.jhigu_fitness.ui.activity.UpdateWorkoutActivity
import com.example.jhigu_fitness.ui.activity.WorkoutDashboard
import com.example.jhigu_fitness.ui.fragment.WorkoutDashboardFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


class ProductAdapter (var context: Context,
                      var data : ArrayList<ProductModel>)
    : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){


    class ProductViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){
        val cardView : CardView = itemView.findViewById(R.id.cardWorkout)
        val imageView : ImageView = itemView.findViewById(R.id.getImage)
        val loading : ProgressBar = itemView.findViewById(R.id.progressBar2)
        var btnEdit : TextView = itemView.findViewById(R.id.btnEdit)
        var productName : TextView = itemView.findViewById(R.id.displayname)
        var productPrice : TextView = itemView.findViewById(R.id.displaySets)
        var productDesc : TextView = itemView.findViewById(R.id.displayDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView : View = LayoutInflater.from(context).inflate(
            R.layout.sample_product,
            parent,false)
        return ProductViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.productName.text = data[position].productName
        holder.productPrice.text = data[position].price.toString()
        holder.productDesc.text = data[position].productDesc

        holder.btnEdit.setOnClickListener {
            val intent = Intent(context, UpdateWorkoutActivity::class.java)
            intent.putExtra("products", data[position].productId)
            context.startActivity(intent)
        }

        holder.cardView.setOnClickListener {
            val intent = Intent(context, WorkoutDashboard::class.java)
            intent.putExtra("id", data[position].productId)
            context.startActivity(intent)
        }

        val imageUrl = data[position].imageUrl
        // Check if the URL is valid (not empty or null)
        if (!imageUrl.isNullOrEmpty()) {
            Picasso.get().load(imageUrl).into(holder.imageView, object : Callback {
                override fun onSuccess() {
                    holder.loading.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    holder.loading.visibility = View.GONE
                    // Optionally, set a placeholder or handle the error
                }
            })
        } else {
            // If the image URL is empty, you can load a placeholder image or handle it accordingly
            Picasso.get().load(R.drawable.placeholder).into(holder.imageView)
            holder.loading.visibility = View.GONE
        }
    }

    fun updateData(products: List<ProductModel>){
        data.clear()
        data.addAll(products)
        notifyDataSetChanged()

    }

    fun getProductId(position: Int) : String{
        return data[position].productId
    }

}