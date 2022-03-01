package com.example.nearbylocations.util.locationlistener

import android.content.Context
import android.location.Address
import android.os.Bundle

import android.location.Geocoder
import android.location.Location

import android.widget.Toast

import android.location.LocationListener
import java.io.IOException
import java.util.*


/*---------- Listener class to get coordinates ------------- */
private class MyLocationListener(private val context: Context) : LocationListener {
    override fun onLocationChanged(loc: Location) {
//        editLocation.setText("")
//        pb.setVisibility(View.INVISIBLE)
//        Toast.makeText(
//            getBaseContext(),
//            "Location changed: Lat: " + loc.getLatitude().toString() + " Lng: "
//                    + loc.getLongitude(), Toast.LENGTH_SHORT
//        ).show()
        val longitude = "Longitude: " + loc.getLongitude()
        val latitude = "Latitude: " + loc.getLatitude()

        /*------- To get city name from coordinates -------- */
        var cityName: String? = null
        val gcd = Geocoder(context, Locale.getDefault())
        val addresses: List<Address>
        try {
            addresses = gcd.getFromLocation(
                loc.getLatitude(),
                loc.getLongitude(), 1
            )
            if (addresses.size > 0) {
                System.out.println(addresses[0].getLocality())
                cityName = addresses[0].getLocality()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val s = (longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName)
//        editLocation.setText(s)
    }

    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
}