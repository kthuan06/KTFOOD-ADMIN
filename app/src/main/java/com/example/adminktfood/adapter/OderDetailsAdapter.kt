package com.example.adminktfood.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminktfood.databinding.ActivityOrderDetailsBinding
import com.example.adminktfood.databinding.OrderDetaillsItemBinding

class OderDetailsAdapter(private var context: Context,
                         private var foodName :ArrayList<String>,
                         private var foodImage :ArrayList<String>,
                         private var foodPrice :ArrayList<String>,
                         private var foodQuantity :ArrayList<Int>,
    ): RecyclerView.Adapter<OderDetailsAdapter.OrderViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OderDetailsAdapter.OrderViewHolder {
        val binding = OrderDetaillsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)

    }

    override fun onBindViewHolder(holder: OderDetailsAdapter.OrderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = foodName.size

     inner  class OrderViewHolder(private val binding: OrderDetaillsItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
            binding.apply {
                textView28.text = foodName[position]
                textView32.text = foodQuantity[position].toString()
                textView33.text = "$"+foodPrice[position]
                val uriS = foodImage[position]
                val uri = Uri.parse(uriS)
                Glide.with(context).load(uri).into(imageView10)
            }
        }

    }
}