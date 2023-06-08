package com.medicify.app.ui.screen.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medicify.app.data.model.DrugItem
import com.medicify.app.data.repository.DrugsRepository
import com.medicify.app.ui.common.UiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(private val drugsRepository: DrugsRepository) : ViewModel() {

    val response: MutableState<UiState<DrugItem>> = mutableStateOf(UiState.Loading)

    fun getDrugsDetail(id: String) {
        viewModelScope.launch {
            drugsRepository.getDrugById(id).onStart {
                response.value = UiState.Loading
            }.catch {
                response.value = UiState.Error(it.toString())
            }.collect {
                response.value = UiState.Success(it.response.data)
            }
        }
    }
}