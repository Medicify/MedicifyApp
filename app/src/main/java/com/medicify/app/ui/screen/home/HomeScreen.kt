package com.medicify.app.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.medicify.app.ui.common.UiState
import com.medicify.app.ui.component.CircularLoading
import com.medicify.app.ui.component.DrugsCardList
import com.medicify.app.ui.component.SearchBar
import com.medicify.app.ui.theme.MedicifyTheme
import com.medicify.app.ui.utils.firstWord
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    navigateToDetail: (String) -> Unit
) {
    val user by remember { mutableStateOf(Firebase.auth.currentUser) }

    when (val result = viewModel.response.value) {
        is UiState.Loading -> {
            CircularLoading(modifier)
            viewModel.getAllDrugs()
        }

        is UiState.Success -> {
            Column(modifier) {
                TopBar(modifier, user)
                DrugsCardList(modifier, result.data, navigateToDetail = navigateToDetail)
            }
        }

        is UiState.Error -> {}
    }
}

@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    user: FirebaseUser?
) {
    Column(
        modifier
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier
                .height(80.dp)
                .padding(8.dp)
                .clickable {  },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val userName = if (user?.displayName != null) {
                ", " + user.displayName!!.firstWord()
            } else {
                " di Medicify"
            }
            if (user?.photoUrl != null) {
                AsyncImage(
                    modifier = modifier
                        .size(55.dp)
                        .clip(CircleShape),
                    model = user.photoUrl,
                    contentDescription = "photo profile"
                )
            }
            Spacer(modifier = modifier.padding(8.dp))
            Text(text = "Selamat Datang$userName", style = MaterialTheme.typography.headlineMedium)
        }
        SearchBar(query = "", onQueryChange = {}, onClearClick = {})
    }
}

@Preview
@Composable
fun TopBarPreview() {
    MedicifyTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            TopBar(user = null)
        }
    }
}





