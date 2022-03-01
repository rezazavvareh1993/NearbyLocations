package com.example.nearbylocations.feature.nearbyplaces

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nearbylocations.data.repository.PlaceRepository
import com.example.nearbylocations.data.local.db.PlaceItem
import com.example.nearbylocations.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearbyPlacesViewModel @Inject constructor(private val repository: PlaceRepository) :
    ViewModel() {

    private val _nearbyPlaces =
        MutableStateFlow<Resource<List<PlaceItem>>>(Resource.Loading())
    val nearbyPlaces = _nearbyPlaces.asStateFlow()
    fun nearbyPlaces(ll: String) {
        viewModelScope.launch {
            repository.nearbyPlaces(ll)
                .flowOn(Dispatchers.IO)
                .collect { _nearbyPlaces.value = it }
        }
    }
}