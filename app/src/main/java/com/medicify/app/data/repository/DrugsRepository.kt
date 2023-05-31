package com.medicify.app.data.repository

import com.medicify.app.data.model.ApiResponse
import kotlinx.coroutines.flow.Flow

interface DrugsRepository {
    fun getAllDrugs(): Flow<ApiResponse>
}