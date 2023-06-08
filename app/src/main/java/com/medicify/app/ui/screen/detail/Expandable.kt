package com.medicify.app.ui.screen.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.medicify.app.data.model.ExpandableItem
import com.medicify.app.ui.theme.MedicifyTheme
import com.medicify.app.ui.utils.PreviewDataSource

@Composable
fun Expandable(
    modifier: Modifier,
    content: String?,
    title: String,
    isExpanded: Boolean,
    onExpandToggle: (Boolean) -> Unit
) {
    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            IconButton(onClick = { onExpandToggle(!isExpanded) }) {
                Icon(
                    imageVector = if (!isExpanded) Icons.Rounded.ExpandMore else Icons.Rounded.ExpandLess,
                    contentDescription = if (!isExpanded) "Baca $title Obat?" else "Tutup $title Obat?"
                )
            }
        }
        AnimatedVisibility(visible = isExpanded, modifier = modifier.animateContentSize()) {
            Text(text = content ?: "-", textAlign = TextAlign.Justify)
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
            val item = remember {
                ExpandableItem(
                    title = "Deskripsi",
                    content = PreviewDataSource.getDrug()[0].description
                )
            }

            Expandable(
                modifier = Modifier,
                content = item.content,
                title = item.title,
                isExpanded = item.isExpanded.value,
                onExpandToggle = {
                    item.isExpanded.value = it
                }
            )
        }
    }

}