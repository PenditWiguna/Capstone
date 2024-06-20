package com.ratan.maigen.additional

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ratan.maigen.R
import com.ratan.maigen.view.activity.DetailActivity
import java.lang.reflect.Member

class ListDestinationAdapter(private val listDestination: ArrayList<Destination>) : RecyclerView.Adapter<ListDestinationAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_destination, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val destination = listDestination[position]

        val photoUrls = holder.itemView.resources.getStringArray(R.array.data_photo)

        Glide.with(holder.itemView.context)
            .load(photoUrls[position])
            .placeholder(R.drawable.air_terjun_aling_aling)
            .into(holder.imgPhoto)

        holder.tvName.text = destination.name
        holder.tvDescription.text = destination.description

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("key_destination", destination)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listDestination.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_destination)
        val tvName: TextView = itemView.findViewById(R.id.tv_namedestination)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_Desc)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Destination)
    }
}