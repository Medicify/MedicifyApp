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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.medicify.app.R
import com.medicify.app.data.model.DrugItem
import com.medicify.app.ui.theme.MedicifyTheme
import com.medicify.app.ui.utils.PreviewDataSource
import com.medicify.app.ui.utils.debugPlaceholder
import com.medicify.app.ui.utils.getFirstWord

@Composable
fun DrugsCardItem(
    modifier: Modifier = Modifier,
    drug: DrugItem,
    navigateToDetail: (String) -> Unit
) {
    Card(
        modifier = modifier
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable { navigateToDetail(drug.title) },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Row(
            modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = drug.imageCustom,
                placeholder = debugPlaceholder(debugPreview = R.drawable.drug_placeholder),
                contentDescription = drug.title,
                contentScale = ContentScale.FillWidth,
                modifier = modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = modifier.padding(4.dp))
            Column(
                modifier = modifier
            ) {
                if (drug.type != null) Text(
                    text = drug.type, fontWeight = FontWeight.Bold, color = colorResource(
                        id = R.color.orange_500
                    )
                )
                Text(
                    text = getFirstWord(drug.title),
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