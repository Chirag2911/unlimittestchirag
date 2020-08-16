package com.hellofresh.chiragtest.network

import com.hellofresh.chiragtest.model.RecipeData
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkManager {
    private val service: NetworkRequest

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(NetworkRequest::class.java)
    }

    suspend fun getRepositories(): List<RecipeData.Items> {
        return service.retrieveRepositories()

    }
}