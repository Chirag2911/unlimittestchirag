package com.unlimit.chiragtest.network

import com.unlimit.chiragtest.database.Repository
import com.unlimit.chiragtest.model.JokesData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkManager {
    private val service: NetworkRequest
    private var repository: Repository? = null
    private val BASE_URL = "https://geek-jokes.sameerkumar.website"

    init {
        val oktHttpClient = OkHttpClient.Builder()
            .addInterceptor(NetworkInterceptor())
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(oktHttpClient.build())
            .build()
        service = retrofit.create(NetworkRequest::class.java)
        repository = Repository()
    }

    suspend fun getJokes(): JokesData{
        return service.retrieveJokesRepositories()
    }




}