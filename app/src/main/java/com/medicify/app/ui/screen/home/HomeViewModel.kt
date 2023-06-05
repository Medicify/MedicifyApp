package com.medicify.app.ui.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medicify.app.data.model.DrugItem
import com.medicify.app.data.repository.DrugsRepository
import com.medicify.app.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(private val drugsRepository: DrugsRepository) : ViewModel() {

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    val response: MutableState<UiState<List<DrugItem>>> = mutableStateOf(UiState.Success(listOf()))

//    fun getAllDrugs() =
//        viewModelScope.launch {
//            drugsRepository.getAllDrugs().onStart {
//                response.value = UiState.Loading
//            }.catch {
//                response.value = UiState.Error(it.toString())
//            }.collect {
//                response.value = UiState.Success(it.response.data)
//            }
//        }

    fun searchDrugs(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        if (newQuery.length > 2) {
            drugsRepository.searchDrugsByName(_query.value).onStart {
                if (newQuery.isNotEmpty()) {
                    response.value = UiState.Loading
                } else {
                    response.value = UiState.Success(listOf())
                }
            }.catch {
                response.value = UiState.Error(it.toString())
            }.collect {
                response.value = UiState.Success(it.response.data)
            }
        }
    }

    fun onClearClick() {
        _query.value = ""
        response.value = UiState.Success(listOf())
    }
}