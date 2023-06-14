package com.medicify.app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.medicify.app.R
import com.medicify.app.data.model.DrugItem
import com.medicify.app.ui.theme.MedicifyTheme
import com.medicify.app.ui.utils.PreviewDataSource
import com.medicify.app.ui.utils.debugPlaceholder
import com.medicify.app.ui.utils.firstWord

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrugsCardItem(
    modifier: Modifier = Modifier,
    drug: DrugItem,
    navigateToDetail: (String) -> Unit,
    clickable: Boolean = true
) {
    Card(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable(enabled = clickable) {
                navigateToDetail(drug.id.trim())
            },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Row(
            modifier
                .semantics(mergeDescendants = true) {
                    contentDescription = "${drug.title.firstWord()}  ${drug.type}"
                }
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = drug.imageCustom,
                placeholder = debugPlaceholder(debugPreview = R.drawable.drug_placeholder),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = modifier
                    .size(80.dp)
                    .drawBehind {
                        this.drawRoundRect(
                            color = Color(109, 137, 232),
                            cornerRadius = CornerRadius(10F, 10F)
                        )
                    }
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = modifier.padding(4.dp))
            Column(
                modifier = modifier
            ) {
                if (drug.type != null) Text(
                    modifier = modifier.semantics { invisibleToUser() },
                    text = drug.type,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(
                        id = R.color.orange_500
                    )
                )
                Text(
                    modifier = modifier.semantics { invisibleToUser() },
                    text = drug.title.firstWord(),
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.red_500)
                )
            }
        }

    }
}

@Preview
@Composable
fun DrugsCardItemPreview() {
    MedicifyTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            DrugsCardItem(
                drug = PreviewDataSource.getDrug()[0],
                navigateToDetail = {})
        }
    }
}