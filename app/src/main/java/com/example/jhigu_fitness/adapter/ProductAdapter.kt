package com.example.jhigu_fitness.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jhigu_fitness.R
import com.example.jhigu_fitness.model.ProductModel
import com.example.jhigu_fitness.ui.activity.UpdateWorkoutActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


class ProductAdapter (var context: Context,
                      var data : ArrayList<ProductModel>)
    : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){


    class ProductViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){
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
            val intent = Intent(context,UpdateWorkoutActivity::class.java)
            intent.putExtra("products",data[position].productId)
            context.startActivity(intent)
        }

        Picasso.get().load(data[position].imageUrl).into(holder.imageView, object : Callback {
            override fun onSuccess() {
                holder.loading.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
            }

        })
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