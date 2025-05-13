package com.example.api_controladorresidencia.Interfaces

import com.example.api_controladorresidencia.Modelos.InquilinoM
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {
    @GET("/api/inquilino")
    fun obtenerInquilinos(): Call<List<InquilinoM>>
}
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8081"

    val instancia: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}



