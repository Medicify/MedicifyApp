package com.medicify.app.ui.screen.home

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

class HomeViewModel(private val drugsRepository: DrugsRepository): ViewModel() {

    val response: MutableState<UiState<List<DrugItem>>> = mutableStateOf(UiState.Loading)

    fun getAllDrugs() =
        viewModelScope.launch {
            drugsRepository.getAllDrugs().onStart {
                response.value= UiState.Loading
            }.catch {
                response.value= UiState.Error(it.toString())
            }.collect {
                response.value=UiState.Success(it.response.data)
            }
        }
}