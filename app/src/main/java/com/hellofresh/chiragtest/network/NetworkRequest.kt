package com.hellofresh.chiragtest.network

import com.hellofresh.chiragtest.model.RecipeData
import io.reactivex.Observable
import retrofit2.http.GET

interface NetworkRequest {

    @GET("/hellofreshdevtests/Chirag2911-android-test/master/recipes.json?token=ADEO7A2G7HHF243HZVWWA7K7H5RFY")
    suspend fun retrieveRepositories(): List<RecipeData>
}