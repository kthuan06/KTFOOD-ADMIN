package com.example.adminktfood.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminktfood.databinding.DeliveryItemBinding

class DeliveryAdapter(
    private val customerName : MutableList<String>,
    private val mnStt : MutableList<Boolean>
    ): RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeliveryAdapter.DeliveryViewHolder {
        val binding  = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryAdapter.DeliveryViewHolder, position: Int) {
       holder.bind(position)
    }

    override fun getItemCount(): Int = customerName.size

    inner class DeliveryViewHolder(private val binding : DeliveryItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                textView24.text = customerName[position]
                if(mnStt[position] == true){
                    textView25.text = "Received"
                }else{
                    textView25.text = "Not Received"
                }
                val colorMap = mapOf(
                    true to Color.GREEN, false to Color.RED
                )
                textView25.setTextColor(colorMap[mnStt[position]]?:Color.BLACK )
                sttColor.backgroundTintList= ColorStateList.valueOf(colorMap[mnStt[position]]?:Color.BLACK)
            }
        }

    }
}