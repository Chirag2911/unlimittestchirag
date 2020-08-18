package com.hellofresh.chiragtest.network
import com.hellofresh.chiragtest.database.Repository
import com.hellofresh.chiragtest.model.RecipeData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NetworkManager {
    private val service: NetworkRequest
    private var repository: Repository? = null

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com"
    }

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

    suspend fun getRepositories(): List<RecipeData> {
        val recipeDataList = service.retrieveRepositories()
        return recipeDataList
    }

}