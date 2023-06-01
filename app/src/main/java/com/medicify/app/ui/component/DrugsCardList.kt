package com.medicify.app.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.medicify.app.data.model.DrugItem
import com.medicify.app.ui.common.UiState

@Composable
fun DrugsCardList(
    modifier: Modifier,
    result: UiState.Success<List<DrugItem>>
) {
    LazyColumn(modifier = modifier) {
        items(result.data, key = { item -> item.id }) { drug ->
            DrugsCardItem(modifier, drug)
        }
    }
}