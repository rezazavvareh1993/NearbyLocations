package com.example.nearbylocations.feature.placedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearbylocations.data.local.db.PlaceItem
import com.example.nearbylocations.data.repository.PlaceRepository
import com.example.nearbylocations.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceDetailViewModel @Inject constructor(private val repository: PlaceRepository) :
    ViewModel() {

    private val _placeDetail =
        MutableStateFlow<Resource<PlaceItem>>(Resource.Loading())
    val placeDetail = _placeDetail.asStateFlow()

    fun placeDetail(fsqId: String) {
        viewModelScope.launch {
            repository.placeDetails(fsqId)
                .flowOn(Dispatchers.IO)
                .collect { _placeDetail.value = it }
        }
    }
}