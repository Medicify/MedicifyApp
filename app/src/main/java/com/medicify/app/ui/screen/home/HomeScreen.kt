package com.medicify.app.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.medicify.app.ui.common.UiState
import com.medicify.app.ui.component.CircularLoading
import com.medicify.app.ui.component.DrugsCardList
import com.medicify.app.ui.component.SearchBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    navigateToDetail: (String) -> Unit
) {
    when (val result = viewModel.response.value) {
        is UiState.Loading -> {
            CircularLoading(modifier)
            viewModel.getAllDrugs()
        }

        is UiState.Success -> {
            Column(modifier) {
                SearchBar(modifier = modifier.padding(horizontal = 8.dp), query = "", onQueryChange = {}, onClearClick = {})
                DrugsCardList(modifier, result.data, navigateToDetail = navigateToDetail)
            }
        }

        is UiState.Error -> {}
    }
}



