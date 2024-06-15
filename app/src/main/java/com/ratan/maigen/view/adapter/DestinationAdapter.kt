package com.ratan.maigen.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ratan.maigen.data.response.ListDestinationItem
import com.ratan.maigen.databinding.ItemDestinationBinding
import com.ratan.maigen.view.activity.MainActivity

class DestinationAdapter(private val recommendations: MainActivity) : PagingDataAdapter<ListDestinationItem, DestinationAdapter.DestinationViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val binding = ItemDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DestinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        val destination = getItem(position)
        if (destination != null) {
            holder.bind(destination)
        }
    }
    
    class DestinationViewHolder(private val binding: ItemDestinationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(destination: ListDestinationItem) {
            binding.tvNamedestination.text = destination.name
            binding.tvDesc.text = destination.description
            Glide.with(binding.imgDestination.context)
                .load(destination)
            // Bind the data here
        }
    }
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListDestinationItem>() {
            override fun areItemsTheSame(oldItem: ListDestinationItem, newItem: ListDestinationItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListDestinationItem, newItem: ListDestinationItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}