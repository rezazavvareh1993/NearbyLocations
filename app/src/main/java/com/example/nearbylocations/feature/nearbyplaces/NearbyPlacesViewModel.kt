package com.example.nearbylocations.feature.nearbyplaces

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearbylocations.data.network.repository.PlaceRepository
import com.example.nearbylocations.pojo.NearbyPlaces
import com.example.nearbylocations.util.response.GeneralResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearbyPlacesViewModel @Inject constructor(private val repository: PlaceRepository) :
    ViewModel() {

    private val _nearbyPlaces =
        MutableStateFlow<GeneralResponse<NearbyPlaces>>(GeneralResponse.Loading())
    val nearbyPlaces = _nearbyPlaces.asStateFlow()
    fun nearbyPlaces() {
        viewModelScope.launch {
            repository.nearbyPlaces()
                .map { GeneralResponse.Success(it) }
                .flowOn(Dispatchers.IO)
                .collect { _nearbyPlaces.value = it }
        }
    }
}