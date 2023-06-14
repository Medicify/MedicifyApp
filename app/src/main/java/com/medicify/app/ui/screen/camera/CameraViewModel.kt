package com.medicify.app.ui.screen.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medicify.app.data.model.DrugItem
import com.medicify.app.data.model.TitleRequestForm
import com.medicify.app.data.repository.DrugsRepository
import com.medicify.app.ui.common.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CameraViewModel(private val drugsRepository: DrugsRepository) : ViewModel() {

    private val _listOfDrugItem: MutableSharedFlow<UiState<List<DrugItem>>> = MutableSharedFlow()
    val listOfDrugItem = _listOfDrugItem.asSharedFlow()


    fun addExtractedText(text: String) {
        viewModelScope.launch {
            drugsRepository.getDrugsFromOCRText(TitleRequestForm(text)).onStart {
                _listOfDrugItem.emit(UiState.Loading)
            }.catch {
                _listOfDrugItem.emit(UiState.Error(it.toString()))
            }.collect {
                _listOfDrugItem.emit(UiState.Success(it.response.data))
            }
        }
    }

    fun clearList() {
        viewModelScope.launch {
            _listOfDrugItem.emit(UiState.Success(listOf()))
        }
    }

}