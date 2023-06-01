package com.medicify.app.data.remote

import com.medicify.app.data.model.ApiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("drugs")
    suspend fun getAllDrugs(): ApiResponse
}