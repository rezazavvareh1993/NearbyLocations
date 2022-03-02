package com.example.nearbylocations.util.extension

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.example.nearbylocations.R

/**
 * This function loads images that api gives us in json and svg form.
 *
 * @param placeholder custom placeholder
 */
fun ImageView.loadImage(imageUrl: String, placeholder: Int? = null) {

    val imageLoader = ImageLoader.Builder(context)
        .componentRegistry { add(SvgDecoder(context)) }
        .build()

    load(uri = imageUrl, imageLoader = imageLoader) {
        crossfade(true)
        placeholder(placeholder ?: R.drawable.ic_location_city)
    }
}