package com.medicify.app.data.remote

import com.medicify.app.data.model.ApiResponse
import com.medicify.app.data.model.IdRequestForm
import com.medicify.app.data.model.TitleRequestForm
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("drugs")
    suspend fun getAllDrugs(): ApiResponse

    @GET("drugs/{id}")
    suspend fun getDrugsById(
        @Path("id") id: String
    ): ApiResponse

    @GET("drugs")
    suspend fun getDrugsByName(
        @Query("title") query: String
    ): ApiResponse

    @POST("ocr")
    suspend fun getDrugsTitleFromOCRText(
        @Body title: TitleRequestForm
    ): ApiResponse

    @POST("recommendation")
    suspend fun getDrugsDetailWithRecommendation(
        @Body id: IdRequestForm
    ): ApiResponse

}