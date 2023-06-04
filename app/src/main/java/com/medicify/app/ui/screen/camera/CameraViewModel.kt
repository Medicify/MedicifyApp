package com.medicify.app.ui.screen.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medicify.app.data.model.DrugItem
import com.medicify.app.data.model.TitleRequestForm
import com.medicify.app.data.repository.DrugsRepository
import com.medicify.app.ui.common.UiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CameraViewModel(private val drugsRepository: DrugsRepository) : ViewModel() {

    private val _title: MutableSharedFlow<UiState<List<DrugItem>>> = MutableSharedFlow()
//    val title = _title.asSharedFlow()


    fun getTitle(text: String): SharedFlow<UiState<List<DrugItem>>> {
        viewModelScope.launch {
            drugsRepository.getTitleFromOCRText(TitleRequestForm(text)).onStart {
                _title.emit(UiState.Loading)
            }.catch {
                _title.emit(UiState.Error(it.toString()))
            }.collect {
                _title.emit(UiState.Success(it.response.data))
            }
        }
        return _title.asSharedFlow()
    }
}