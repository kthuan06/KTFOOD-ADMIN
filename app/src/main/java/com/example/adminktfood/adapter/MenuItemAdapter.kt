package com.example.adminktfood.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminktfood.databinding.ItemMenuBinding
import com.example.adminktfood.model.AllMenu
import com.google.firebase.database.DatabaseReference

class MenuItemAdapter(private val context: Context,
                      private val menuList: ArrayList<AllMenu>,
                      databaseReference:  DatabaseReference,
                      private val onDeleteClickListener:(position : Int) -> Unit
    ): RecyclerView.Adapter<MenuItemAdapter.AddItemViewHolder>() {

        private val itemQuantities = IntArray(menuList.size){1}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding)
    }

    override fun getItemCount(): Int =menuList.size

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class AddItemViewHolder(private val binding: ItemMenuBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int){
            binding.apply {
                val menuItem = menuList[position]
                val uriString = menuItem.foodImage
                val uri = Uri.parse(uriString)
                textView19.text = menuItem.foodName
                textView18.text = "$"+menuItem.foodPrice
                Glide.with(context).load(uri).into(imageFood)
                //sub
                val quantity = itemQuantities[position]
                textView20.text = quantity.toString()
                imageButton.setOnClickListener {
                    if(itemQuantities[position] > 1){
                        itemQuantities[position]++
                        textView20.text = itemQuantities[position].toString()
                    }
                }
                //add
                imageButton2.setOnClickListener {
                    if(itemQuantities[position] <10){
                        itemQuantities[position]++
                        textView20.text = itemQuantities[position].toString()
                    }
                }
                //delete
                imageButton3.setOnClickListener {
                    onDeleteClickListener(position)
                    menuList.removeAt(position)
                    menuList.removeAt(position)
                    menuList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, menuList.size)
                }
            }
        }
    }
}