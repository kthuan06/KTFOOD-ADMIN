package com.example.adminktfood.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminktfood.databinding.PendingItemBinding
import java.net.URI

class PendingOrderAdapter(
    private val context : Context,
    private val customerNames : MutableList<String>,
    private val quantity : MutableList<String>,
    private val foodImage : MutableList<String>,
    private val itemClicked : OnItemClicked


) : RecyclerView.Adapter<PendingOrderAdapter.PendingOrderViewHolder>() {

    interface OnItemClicked{
        fun onItemClickListener(position: Int)
        fun onItemAcceptClickListener(position: Int)
        fun onItemDispatchClickListener(position: Int)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendingOrderAdapter.PendingOrderViewHolder {
        val binding = PendingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PendingOrderViewHolder(binding)

    }

    override fun onBindViewHolder(
        holder: PendingOrderAdapter.PendingOrderViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = customerNames.size

    inner class PendingOrderViewHolder(private val binding: PendingItemBinding):RecyclerView.ViewHolder(binding.root) {
        private var isAccepted = false
        fun bind(position: Int) {
            binding.apply {
                textView29.text = customerNames[position]
                textView30.text = quantity[position]
                val uriS = foodImage[position]
                val uri = Uri.parse(uriS)
                Glide.with(context).load(uri).into(imageView11)

                button3.apply {
                    if(!isAccepted){
                        text = "Accept"
                    }else{
                        text = "Dispatch"
                    }
                    setOnClickListener {
                        if(!isAccepted){
                            text = "Dispatch"
                            isAccepted = true
                            Toast.makeText(context, "Is accepted", Toast.LENGTH_SHORT).show()
                            itemClicked.onItemAcceptClickListener(position)
                        }else{
                            customerNames.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            Toast.makeText(context, "Is dispatched", Toast.LENGTH_SHORT).show()
                            itemClicked.onItemDispatchClickListener(position)
                        }
                    }

                }
                itemView.setOnClickListener {
                    itemClicked.onItemClickListener(position)
                }
            }

        }

    }
}
