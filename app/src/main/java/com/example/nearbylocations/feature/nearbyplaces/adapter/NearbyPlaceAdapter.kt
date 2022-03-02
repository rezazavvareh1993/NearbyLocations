package com.example.nearbylocations.feature.nearbyplaces.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nearbylocations.databinding.ItemNearbyPlacesListBinding
import com.example.nearbylocations.data.local.db.PlaceItem
import com.example.nearbylocations.util.extension.loadImage

class NearbyPlaceAdapter(private val getItem: (String) -> Unit) :
    ListAdapter<PlaceItem, NearbyPlaceAdapter.NearbyPlaceViewHolder>(
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
        fun bindData(data: PlaceItem) {
            data.distance?.let { binding.distance.text = it }
            data.name?.let { binding.name.text = it }
            data.address?.let { binding.address.text = it }
            data.icon?.let { binding.icon.loadImage(it) }
            itemView.setOnClickListener { getItem(data.fsqId) }
        }
    }
}

class NearbyPlaceDiffUtil : DiffUtil.ItemCallback<PlaceItem>() {
    override fun areItemsTheSame(
        oldItem: PlaceItem,
        newItem: PlaceItem
    ) = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: PlaceItem,
        newItem: PlaceItem
    ) = oldItem.fsqId == newItem.fsqId
}