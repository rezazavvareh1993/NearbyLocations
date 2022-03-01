package com.example.nearbylocations.feature.nearbyplaces.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nearbylocations.databinding.ItemNearbyPlacesListBinding
import com.example.nearbylocations.pojo.Result
import com.example.nearbylocations.util.extension.loadImage

class NearbyPlaceAdapter(private val getItem: (String) -> Unit) :
    ListAdapter<Result, NearbyPlaceAdapter.NearbyPlaceViewHolder>(
        NearbyPlaceDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyPlaceViewHolder {
        return NearbyPlaceViewHolder(
            ItemNearbyPlacesListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false,
            ), getItem
        )
    }

    override fun onBindViewHolder(holder: NearbyPlaceViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    class NearbyPlaceViewHolder(
        private val binding: ItemNearbyPlacesListBinding,
        private val getItem: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: Result) {
            data.distance?.let { binding.distance.text = it.toString() }
            data.name?.let { binding.name.text = it }
            data.location?.address?.let { binding.address.text = it }
            if (!data.categories.isNullOrEmpty())
                data.categories.first().icon?.let {
                    binding.icon.loadImage("${it.prefix}${it.suffix}")
                }
            itemView.setOnClickListener { getItem(data.fsq_id) }
        }
    }
}

class NearbyPlaceDiffUtil : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(
        oldItem: Result,
        newItem: Result
    ) = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: Result,
        newItem: Result
    ) = oldItem.fsq_id == newItem.fsq_id
}