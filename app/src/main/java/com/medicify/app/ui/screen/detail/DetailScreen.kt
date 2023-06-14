package com.medicify.app.ui.screen.detail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.medicify.app.data.model.DrugsDetailResponse
import com.medicify.app.data.model.IdRequestForm
import com.medicify.app.ui.common.UiState
import com.medicify.app.ui.component.CircularLoading
import com.medicify.app.ui.component.DrugsCardItem
import com.medicify.app.ui.theme.MedicifyTheme
import com.medicify.app.ui.utils.PreviewDataSource
import com.medicify.app.ui.utils.firstWord
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    id: String,
    detailViewModel: DetailViewModel = koinViewModel(),
    onClosePressed: () -> Unit,
) {
    Log.d("okhttp", "DetailScreen: $id")

    LaunchedEffect(key1 = true) {
        detailViewModel.getDrugsDetailWithRecommendation(IdRequestForm(id))
    }

    when (val result = detailViewModel.response.value) {
        is UiState.Loading -> {
            CircularLoading(modifier)
        }

        is UiState.Success -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = modifier.padding(horizontal = 8.dp),
                        title = {},
                        navigationIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Info,
                                contentDescription = "Terdeteksi ${result.data.data.title.firstWord()}",
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        },
                        actions = {
                            IconButton(
                                onClick = onClosePressed
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = "Scan Ulang Obat?",
                                )
                            }
                        }
                    )
                }
            ) {
                LazyColumn(
                    modifier
                        .padding(it)
                ) {
                    item {
                        DetailContent(modifier, result.data, detailViewModel)
                    }
                    if (result.data.recommendation != null) {
                        items(result.data.recommendation, key = { item -> item.id }) { drug ->
                            DrugsCardItem(modifier, drug, navigateToDetail = {}, clickable = false)
                        }
                    }
                }
            }
        }

        is UiState.Error -> {}
    }
}

@Composable
private fun DetailContent(
    modifier: Modifier,
    drugResponse: DrugsDetailResponse,
    detailViewModel: DetailViewModel,
) {
    detailViewModel.expandableItems.collectAsState(initial = UiState.Loading).value.let { expandableItems ->
        when (expandableItems) {
            is UiState.Loading -> {
                detailViewModel.setExpandableItems(drugResponse.data)
            }

            is UiState.Success -> {
                Column(
                    modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = modifier
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 24.dp,
                            )
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = drugResponse.data.imageCustom,
                            modifier = modifier
                                .size(200.dp)
                                .drawBehind {
                                    this.drawRoundRect(
                                        color = Color(109, 137, 232),
                                        cornerRadius = CornerRadius(10F, 10F)
                                    )
                                },
                            contentDescription = "gambar kemasan ${drugResponse.data.title.firstWord()}"
                        )
                    }
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = drugResponse.data.title.firstWord(),
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Text(
                            text = drugResponse.data.type ?: "",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    expandableItems.data.forEach { item ->
                        Expandable(
                            modifier,
                            content = item.content,
                            title = item.title,
                            isExpanded = item.isExpanded.value,
                            onExpandToggle = {
                                item.isExpanded.value = it
                            }
                        )
                    }
                    Spacer(modifier = modifier.padding(16.dp))
                    Text(
                        text = "Rekomendasi Obat Serupa :",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            is UiState.Error -> {}
        }
    }

}

@Preview
@Composable
fun DetailContentPreview() {
    MedicifyTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            DetailContent(
                modifier = Modifier,
                drugResponse = DrugsDetailResponse(
                    0,
                    PreviewDataSource.getDrug()[0],
                    PreviewDataSource.getDrug()
                ),
                detailViewModel = koinViewModel()
            )
        }
    }
}