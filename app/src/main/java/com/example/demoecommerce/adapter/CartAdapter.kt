package com.example.demoecommerce.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.demoecommerce.activity.ProductDetailsActivity
import com.example.demoecommerce.databinding.LayoutCartItemBinding
import com.example.demoecommerce.model.AppDatabase
import com.example.demoecommerce.roomdb.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdapter(val context : Context, val list : List<ProductModel>) :
    Adapter<CartAdapter.CartViewHolder>() {
    inner class CartViewHolder(val binding : LayoutCartItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = LayoutCartItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Glide.with(context).load(list[position].productImage).into(holder.binding.imageView4)
        holder.binding.textView7.text = list[position].productName
        holder.binding.textView8.text = list[position].productSp

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)
        }

        val dao = AppDatabase.getInstance(context).productDao()
        holder.binding.imageDelete.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                dao.deleteProduct(ProductModel(list[position].productId,
                    list[position].productName,
                    list[position].productImage,
                    list[position].productSp))
            }
        }
    }
}