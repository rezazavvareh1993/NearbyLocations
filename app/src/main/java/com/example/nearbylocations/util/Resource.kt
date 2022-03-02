package com.example.nearbylocations.util

/**
 * When a service gets called, the response is wrapped by this model.
 * The response status can be [Success] or [Loading].
 *
 * @property data the value of response from [T] type.
 */
sealed class Resource<T>(val data: T? = null, val throwable: Throwable? = null) {

    /**
     * When we get the response successfully.
     *
     * @param T the type of response model ex
     * @property data the value of response from [T] type.
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * When we want to show the loading indicator.
     *
     * @param T the type of response model ex
     * @property data the value of response from [T] type.
     */
    class Loading<T>(data: T? = null) : Resource<T>(data)

    /**
     * When we have error
     *
     * @param T the type of response model ex
     * @property data the value of response from [T] type.
     * @param message the exception message
     */
    class Error<T>(data: T?, throwable: Throwable) : Resource<T>(data = data, throwable = throwable)
}