package com.ratan.maigen.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ratan.maigen.data.response.ExploreResult
import com.ratan.maigen.databinding.ItemDestinationBinding

class ExploreAdapter : RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {

    private var exploreList = listOf<ExploreResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val binding = ItemDestinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExploreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        val exploreItem = exploreList[position]
        holder.bind(exploreItem)
    }

    override fun getItemCount(): Int {
        return exploreList.size
    }

    fun submitList(list: List<ExploreResult>) {
        exploreList = list
        notifyDataSetChanged()
    }

    class ExploreViewHolder(private val binding: ItemDestinationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(exploreItem: ExploreResult) {
            binding.tvNamedestination.text = exploreItem.Place_Name
            binding.tvCreate.text = exploreItem.City
            binding.tvDesc.text = exploreItem.Description
            Glide.with(binding.imgDestination.context)
                .load(exploreItem.Gambar)
                .into(binding.imgDestination)
        }
    }
}