package com.example.nearbylocations.util

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.nearbylocations.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Used for collect sharedFlow data from fragment
 **/
fun <T> Fragment.collectLifecycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collect)
        }
    }
}

/**
 * Show snackBar message.
 *
 * @param message Message to show as snackBar
 */
fun View.showSnackMessage(message: String) {
    val snackBar = createSnackBar(message)
    val snackBarTitle: TextView = snackBar.view.findViewById(R.id.snackbar_text)
    snackBarTitle.layoutDirection = View.LAYOUT_DIRECTION_RTL
    snackBar.show()
}

/**
 * Create [Snackbar] instance
 *
 * @return [Snackbar] instance
 */
fun View.createSnackBar(message: String): Snackbar {
    return Snackbar.make(this, message, Snackbar.LENGTH_LONG)
}