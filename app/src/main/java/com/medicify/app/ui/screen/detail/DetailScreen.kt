package com.medicify.app.ui.screen.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.medicify.app.data.model.DrugItem
import com.medicify.app.data.model.ExpandableItem
import com.medicify.app.ui.common.UiState
import com.medicify.app.ui.component.CircularLoading
import com.medicify.app.ui.theme.MedicifyTheme
import com.medicify.app.ui.utils.PreviewDataSource
import com.medicify.app.ui.utils.firstWord
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    id: String,
    detailViewModel: DetailViewModel = koinViewModel()
) {
    when (val result = detailViewModel.response.value) {
        is UiState.Loading -> {
            CircularLoading(modifier)

            detailViewModel.getDrugsDetail(id)
        }

        is UiState.Success -> {
            DetailContent(modifier, result.data)
        }

        is UiState.Error -> {}
    }
}

@Composable
private fun DetailContent(
    modifier: Modifier,
    drug: DrugItem
) {
    val expandableItems = remember {
        arrayListOf(
            ExpandableItem(title = "Deskripsi", content = drug.description),
            ExpandableItem(title = "Indikasi", content = drug.indication),
            ExpandableItem(title = "Aturan Pakai", content = drug.howToUse),
            ExpandableItem(title = "Dosis", content = drug.dose),
            ExpandableItem(title = "Kontra Indikasi", content = drug.indicationContra),
        )
    }

    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = drug.image,
                modifier = modifier
                    .size(200.dp)
                    .drawBehind {
                        this.drawRoundRect(
                            color = Color(109, 137, 232),
                            cornerRadius = CornerRadius(10F, 10F)
                        )
                    },
                contentDescription = "gambar kemasan ${drug.title.firstWord()}"
            )
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = drug.title.firstWord(), style = MaterialTheme.typography.headlineLarge)
            Text(text = drug.type ?: "", style = MaterialTheme.typography.headlineMedium)
        }
        expandableItems.forEach { item ->
            Expandable(modifier, content = item.content, title = item.title)
        }
    }
}

@Composable
private fun Expandable(
    modifier: Modifier,
    content: String?,
    title: String,
) {
    var isExpanded by remember { mutableStateOf(false) }
    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (!isExpanded) Icons.Rounded.ExpandMore else Icons.Rounded.ExpandLess,
                    contentDescription = if (!isExpanded) "Baca $title Obat?" else "Tutup $title Obat?"
                )
            }
        }
        AnimatedVisibility(visible = isExpanded, modifier = modifier.animateContentSize()) {
            Text(text = content ?: "", textAlign = TextAlign.Justify)
        }
    }
}

@Preview
@Composable
fun ExpandablePreview() {
    MedicifyTheme {
        Surface(
            color = MaterialTheme.colorScheme.background,
        ) {
            Expandable(
                modifier = Modifier,
                content = PreviewDataSource.getDrug()[0].description,
                title = "Deskirpsi"
            )
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
            DetailContent(modifier = Modifier, drug = PreviewDataSource.getDrug()[0])
        }
    }
}