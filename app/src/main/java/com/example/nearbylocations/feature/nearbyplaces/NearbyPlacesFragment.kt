package com.example.nearbylocations.feature.nearbyplaces

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.nearbylocations.R
import com.example.nearbylocations.base.BaseFragment
import com.example.nearbylocations.databinding.FragmentNearbyPlacesBinding
import com.example.nearbylocations.feature.nearbyplaces.adapter.NearbyPlaceAdapter
import com.example.nearbylocations.util.collectLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint

import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService
import com.example.nearbylocations.util.showSnackMessage
import java.util.*

const val LOCATION_PERMISSION_REQUEST = 101
/**
 * A simple [Fragment] subclass.
 * Use the [NearbyPlacesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class NearbyPlacesFragment :
    BaseFragment<FragmentNearbyPlacesBinding>(R.layout.fragment_nearby_places) {
    private val viewModel: NearbyPlacesViewModel by viewModels()
    private lateinit var adapter: NearbyPlaceAdapter
    private var location = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listeners()
        observers()
    }

    private fun init() {
        checkPermission()
        getCurrentLocation()
//        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE)
//        locationManager = (LocationManager)
//        getSystemService(Context.LOCATION_SERVICE);

        setUpRecyclerView()
        viewModel.nearbyPlaces()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (ContextCompat.checkSelfPermission(
                        requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_PERMISSION_REQUEST
                    )
                } else {
                    getLocation()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }else {
            getLocation()
        }
    }

    private fun getCurrentLocation() {
        val listener = LocationListener {
            location = "${it.latitude},${it.longitude}"
            requireView().showSnackMessage(location)
        }
    }

    fun getLocation() {

        var locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager?

        var locationListener = object : LocationListener{

            override fun onLocationChanged(location: Location) {
                var latitute = location!!.latitude
                var longitute = location!!.longitude

                Log.i("test", "Latitute: $latitute ; Longitute: $longitute")
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onProviderDisabled(provider: String) {
            }

        }

        try {
            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch (ex:SecurityException) {
            Toast.makeText(requireContext(), "Fehler bei der Erfassung!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpRecyclerView() {
        adapter = NearbyPlaceAdapter {
            requireView().showSnackMessage(it)
        }

        binding.nearbyPlacesRecycler.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST -> getCurrentLocation()
        }
    }

    private fun listeners() {

    }

    private fun observers() {
        collectLifecycleFlow(viewModel.nearbyPlaces) { response ->
            response.data?.results?.let {
                if (it.isNotEmpty()) {
                    adapter.submitList(it)
                }
            }
        }
    }
}