package com.medicify.app.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.medicify.app.data.model.DrugItem
import com.medicify.app.ui.theme.MedicifyTheme
import com.medicify.app.ui.utils.PreviewDataSource

@Composable
fun DrugsCardList(
    modifier: Modifier = Modifier,
    drugList: List<DrugItem>,
    navigateToDetail: (String) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(drugList, key = { item -> item.id }) { drug ->
            DrugsCardItem(modifier, drug, navigateToDetail = navigateToDetail)
        }
    }
}

@Preview
@Composable
fun DrugCardListPreview() {
    MedicifyTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            DrugsCardList(
                drugList = PreviewDataSource.getDrug(),
                navigateToDetail = {})
        }
    }
}