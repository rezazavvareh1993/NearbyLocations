package com.example.nearbylocations.feature.nearbyplaces

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.nearbylocations.base.BaseFragment
import com.example.nearbylocations.databinding.FragmentNearbyPlacesBinding
import com.example.nearbylocations.feature.nearbyplaces.adapter.NearbyPlaceAdapter
import com.example.nearbylocations.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import android.widget.Toast
import com.example.nearbylocations.R


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
    private var mCurrentLocationCoordinate = "41.8781,-87.6298"
//    private lateinit var client: FusedLocationProviderClient
    private var currentLocation: Location? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        listeners()
        observers()
    }

    private fun listeners() {
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        client.lastLocation.addOnSuccessListener(
//            requireActivity(),
//            object : OnSuccessListener<Location> {
//                override fun onSuccess(p0: Any?) {
//                    if (location != null && location.distanceTo(currentLocation) > 100) {
//                        currentLocation = location
//                        mCurrentLocationCoordinate =
//                            "${currentLocation.latitude},${currentLocation.longitude}"
//                        viewModel.nearbyPlaces(mCurrentLocationCoordinate)
//                    }
//                }
//            }
//        )

//        client.lastLocation.addOnSuccessListener(requireActivity()
//        ) { location ->
//            if (location != null && location.distanceTo(currentLocation) > 100) {
//                currentLocation = location
//                mCurrentLocationCoordinate =
//                    "${currentLocation.latitude},${currentLocation.longitude}"
//                viewModel.nearbyPlaces(mCurrentLocationCoordinate)
//            }
//        }



//        var locationListener = object : LocationListener {
//
//            override fun onLocationChanged(location: Location) {
//                if (location != null && location.distanceTo(currentLocation) > 100) {
//                    // do update stuff
//                    currentLocation = location
//                    mCurrentLocationCoordinate =
//                        "${currentLocation.latitude},${currentLocation.longitude}"
//                    viewModel.nearbyPlaces(mCurrentLocationCoordinate)
//                }
//            }
//
//            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
//            }
//
//            override fun onProviderEnabled(provider: String) {
//            }
//
//            override fun onProviderDisabled(provider: String) {
//            }
//
//        }

    }

    private fun init() {
        checkPermission()
        setUpRecyclerView()
        viewModel.nearbyPlaces(mCurrentLocationCoordinate)
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
        } else {
            getLocation()
        }
    }

    private fun getLocation() {

        val locationManager =
            requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager?

        val listener = LocationListener { location ->
            if(currentLocation == null) {
                currentLocation = location
                mCurrentLocationCoordinate =
                    "${location!!.latitude},${location!!.longitude}"
                viewModel.nearbyPlaces(mCurrentLocationCoordinate)
            } else {
                if (location.distanceTo(currentLocation) > 100) {
                    // do update stuff
                    currentLocation = location
                    mCurrentLocationCoordinate =
                        "${currentLocation!!.latitude},${currentLocation!!.longitude}"
                    viewModel.nearbyPlaces(mCurrentLocationCoordinate)
                }
            }
            requireView().showSnackMessage(mCurrentLocationCoordinate)
        }

        try {
            locationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 100f, listener)
        } catch (ex:SecurityException) {
            Toast.makeText(requireContext(), "Fehler bei der Erfassung!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpRecyclerView() {
        adapter = NearbyPlaceAdapter {
            requireView().showSnackMessage(it)
            findNavController().navigate(
                NearbyPlacesFragmentDirections.actionNearbyPlacesFragmentToPlaceDetailFragment(
                    fsqId = it
                )
            )
        }

        binding.nearbyPlacesRecycler.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST -> getLocation()
        }
    }

//    fun getDistance(LatLng1: LatLng, LatLng2: LatLng): Double {
//        var distance = 0.0
//        val locationA = Location("A")
//        locationA.latitude = LatLng1.latitude
//        locationA.longitude = LatLng1.longitude
//        val locationB = Location("B")
//        locationB.latitude = LatLng2.latitude
//        locationB.longitude = LatLng2.longitude
//        distance = locationA.distanceTo(locationB).toDouble()
//        return distance
//    }

    private fun observers() {
        collectLifecycleFlow(viewModel.nearbyPlaces) { state ->
            when (state) {
                is Resource.Success -> {
                    state.data?.let {
                        binding.progressBar.makeGone()
                        adapter.submitList(it)
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.makeGone()

//                    binding.progressBar.makeVisible()
                    if (!state.data.isNullOrEmpty())
                        adapter.submitList(state.data)
                }
                is Resource.Error -> {
                    binding.progressBar.makeGone()
                    requireView().showSnackMessage("خطایی رخ داده است")

//                    if (!state.data.isNullOrEmpty())
//                        adapter.submitList(state.data)
//                    else
//                        requireView().showSnackMessage("خطایی رخ داده است")
                }
            }
        }
    }
}