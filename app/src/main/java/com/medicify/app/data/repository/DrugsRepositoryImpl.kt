package com.medicify.app.data.repository

import com.medicify.app.data.model.ApiResponse
import com.medicify.app.data.model.TitleRequestForm
import com.medicify.app.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DrugsRepositoryImpl(
    private val apiService: ApiService,
): DrugsRepository {

    override fun searchDrugsByName(query : String) : Flow<ApiResponse> = flow {
        emit(apiService.getDrugsByName(query))
    }.flowOn(Dispatchers.IO)

    override fun getAllDrugs(): Flow<ApiResponse> = flow {
        emit(apiService.getAllDrugs())
    }.flowOn(Dispatchers.IO)

    override suspend fun getTitleFromOCRText(text: TitleRequestForm) : Flow<ApiResponse> = flow {
        emit(apiService.getDrugsTitleFromOCRText(text))
    }.flowOn(Dispatchers.IO)

}