package com.unlimit.chiragtest.network

import com.unlimit.chiragtest.model.JokesData
import retrofit2.http.GET

interface NetworkRequest {

    @GET("/api?format=json")
    suspend fun retrieveJokesRepositories(): JokesData

}