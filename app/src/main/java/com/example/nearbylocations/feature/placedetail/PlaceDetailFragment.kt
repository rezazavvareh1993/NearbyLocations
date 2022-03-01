package com.example.nearbylocations.feature.placedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.nearbylocations.R
import com.example.nearbylocations.base.BaseFragment
import com.example.nearbylocations.data.local.db.PlaceItem
import com.example.nearbylocations.databinding.FragmentPlaceDetailBinding
import com.example.nearbylocations.feature.nearbyplaces.NearbyPlacesViewModel
import com.example.nearbylocations.util.*
import com.example.nearbylocations.util.extension.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceDetailFragment :
    BaseFragment<FragmentPlaceDetailBinding>(R.layout.fragment_place_detail) {
    private val viewModel: PlaceDetailViewModel by viewModels()
    private val args: PlaceDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listeners()
        observers()
    }

    private fun init() {
        viewModel.placeDetail(args.fsqId)
    }

    private fun listeners() {}
    private fun observers() {
        collectLifecycleFlow(viewModel.placeDetail) { state ->
            when (state) {
                is Resource.Success -> {
                    state.data?.let {
                        binding.progressBar.makeGone()
                        binding.group.makeVisible()
                        bindDataToView(it)
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.makeVisible()
                }
                is Resource.Error -> {
                    binding.progressBar.makeGone()
                    binding.group.makeVisible()
                    if(state.data != null)
                        bindDataToView(state.data)
                    else
                        requireView().showSnackMessage("خطایی رخ داده است")
                }
            }
        }
    }

    private fun bindDataToView(placeItem: PlaceItem) {
        placeItem.placeDetail?.let { placeDetail ->
            placeDetail.name?.let { binding.title.text = it }
            placeDetail.location?.let { binding.address.text = it.address }
            placeItem.icon?.let {
                binding.icon.loadImage(it)
            }
        }
    }
}