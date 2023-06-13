package com.medicify.app.ui.screen.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medicify.app.data.model.DrugItem
import com.medicify.app.data.model.DrugsDetailResponse
import com.medicify.app.data.model.ExpandableItem
import com.medicify.app.data.model.IdRequestForm
import com.medicify.app.data.repository.DrugsRepository
import com.medicify.app.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(
    private val drugsRepository: DrugsRepository
) : ViewModel() {

    val response: MutableState<UiState<DrugsDetailResponse>> = mutableStateOf(UiState.Loading)

    fun getDrugsDetailWithRecommendation(id: IdRequestForm) {
        viewModelScope.launch {
            drugsRepository.getDrugsDetailWithRecommendation(id).onStart {
                response.value = UiState.Loading
            }.catch {
                response.value = UiState.Error(it.toString())
            }.collect {
                response.value = UiState.Success(it.response)
            }
        }
    }

    private val _expandableItems = MutableStateFlow<UiState<List<ExpandableItem>>>(UiState.Loading)

    val expandableItems: Flow<UiState<List<ExpandableItem>>>
        get() = _expandableItems


    fun setExpandableItems(drug: DrugItem) {
        val expandableItems = arrayListOf(
            ExpandableItem(title = "Deskripsi", content = drug.description),
            ExpandableItem(title = "Indikasi", content = drug.indication),
            ExpandableItem(title = "Aturan Pakai", content = drug.howToUse),
            ExpandableItem(title = "Dosis", content = drug.dose),
            ExpandableItem(title = "Kontra Indikasi", content = drug.indicationContra),
        )
        _expandableItems.value = UiState.Success(expandableItems)
    }
}