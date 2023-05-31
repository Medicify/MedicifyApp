package com.medicify.app.data.remote

import com.medicify.app.data.model.DrugItem
import retrofit2.http.GET

interface ApiService {
    @GET("drugs")
    suspend fun getAllDrugs(): List<DrugItem>
}