package com.example.nearbylocations.di

import com.example.nearbylocations.data.network.retrofit.PlacesApi
import com.example.nearbylocations.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Provides [Retrofit] interfaces
 */

private const val BASE_URL = "https://api.foursquare.com/v3/places/"

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(moshi: MoshiConverterFactory, httpClient: OkHttpClient.Builder): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(moshi)
            .client(httpClient.build())
            .build()

    @Singleton
    @Provides
    fun provideOkHttp(interceptor: HttpLoggingInterceptor) = OkHttpClient().newBuilder().apply {
        connectTimeout(15, TimeUnit.SECONDS)
        readTimeout(15, TimeUnit.SECONDS)
        callTimeout(15, TimeUnit.SECONDS)
        addInterceptor(interceptor)
        interceptors().add(
            Interceptor { chain ->
                val request = chain.request()
                val requestBuilder = request.newBuilder()
                    // add default shared headers to every http request
                    // add tokenType and token to Authorization header of request
                    .addHeader("authorization", Constants.API_KEY)
                    .method(request.method, request.body)
                chain.proceed(requestBuilder.build())
            }
        )
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun getNearbyPlaceRetrofitInterface(retrofit: Retrofit): PlacesApi =
        retrofit.create(PlacesApi::class.java)
}
