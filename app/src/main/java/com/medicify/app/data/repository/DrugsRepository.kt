package com.medicify.app.data.repository

import com.medicify.app.data.model.ApiDetailResponse
import com.medicify.app.data.model.ApiResponse
import com.medicify.app.data.model.IdRequestForm
import com.medicify.app.data.model.TitleRequestForm
import kotlinx.coroutines.flow.Flow

interface DrugsRepository {
    fun getAllDrugs(): Flow<ApiResponse>

    suspend fun getDrugsFromOCRText(text: TitleRequestForm): Flow<ApiResponse>

    fun searchDrugsByName(query: String): Flow<ApiResponse>

    suspend fun getDrugsDetailWithRecommendation(id: IdRequestForm): Flow<ApiDetailResponse>
}