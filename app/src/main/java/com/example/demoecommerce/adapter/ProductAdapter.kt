package com.example.demoecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.demoecommerce.activity.ProductDetailsActivity
import com.example.demoecommerce.databinding.LayoutProductItemBinding
import com.example.demoecommerce.model.AddProductModel

class ProductAdapter(val context : Context, val list : ArrayList<AddProductModel>) :
    Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(val binding : LayoutProductItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = LayoutProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val data = list[position]
        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView2)
        holder.binding.productName.text = data.productName
        holder.binding.categoryText.text = data.productCategory
        holder.binding.priceText.text = data.productMrp
        holder.binding.button.text = data.productSp

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)
        }
    }
}