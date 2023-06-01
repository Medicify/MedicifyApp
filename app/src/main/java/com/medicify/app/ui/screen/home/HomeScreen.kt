package com.medicify.app.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.medicify.app.ui.common.UiState
import com.medicify.app.ui.component.CircularLoading
import com.medicify.app.ui.component.DrugsCardList
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    when (val result = viewModel.response.value) {
        is UiState.Loading -> {
            CircularLoading(modifier)
            viewModel.getAllDrugs()
        }

        is UiState.Success -> {
            DrugsCardList(modifier, result.data)
        }
        is UiState.Error -> {}
    }
}

