package com.example.nearbylocations.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading())
        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (t: Throwable) {
            query().map {
                Resource.Error(data, throwable = t)
            }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}