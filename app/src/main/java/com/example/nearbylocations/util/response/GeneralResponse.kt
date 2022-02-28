package com.example.nearbylocations.util.response

/**
 * When a service gets called, the response is wrapped by this model.
 * The response status can be [Success] or [Loading].
 *
 * @property data the value of response from [T] type.
 */
sealed class GeneralResponse<T>(val data: T? = null, val message: String? = null) {

    /**
     * When we get the response successfully.
     *
     * @param T the type of response model ex:[DictionaryResult] and its generic.
     * @property data the value of response from [T] type.
     */
    class Success<T>(data: T) : GeneralResponse<T>(data)

    /**
     * When we want to show the loading indicator.
     *
     * @param T the type of response model ex:[DictionaryResult] and its generic.
     * @property data the value of response from [T] type.
     */
    class Loading<T>(data: T? = null) : GeneralResponse<T>(data)

    /**
     * When we have error
     *
     * @param T the type of response model ex:[DictionaryResult] and its generic.
     * @property data the value of response from [T] type.
     * @param message the exception message
     */
    class IOError<T>(message: String) : GeneralResponse<T>(message = message)

    /**
     * When we have an UnAuthorized error
     *
     * @param message the exception message
     */
    class UnAuthorizeError<T>(message: String) : GeneralResponse<T>(message = message)
}