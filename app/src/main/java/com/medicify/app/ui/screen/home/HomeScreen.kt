package com.medicify.app.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseUser
import com.medicify.app.R
import com.medicify.app.di.appModule
import com.medicify.app.ui.common.UiState
import com.medicify.app.ui.component.CircularLoading
import com.medicify.app.ui.theme.MedicifyTheme
import com.medicify.app.ui.utils.firstWord
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel(),
    navigateToDetail: (String) -> Unit,
    user: FirebaseUser?
) {
    val query by homeViewModel.query.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Column(modifier) {
        Column(
            modifier
                .padding(horizontal = 8.dp)
        ) {
            Row(
                modifier
                    .height(80.dp)
                    .padding(8.dp),
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
                Text(
                    text = "Selamat Datang$userName",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            SearchBar(
                modifier.focusRequester(focusRequester),
                query = query,
                onQueryChange = { newQuery ->
                    homeViewModel.searchDrugs(newQuery)

                },
                onClearClick = {
                    homeViewModel.onClearClick()
                    focusManager.clearFocus()
                }
            )
        }
        when (val result = homeViewModel.response.value) {
            is UiState.Loading -> {
                CircularLoading(modifier)
            }

            is UiState.Success -> {
                if (result.data.isNotEmpty()) {
                    DrugsCardList(modifier, result.data, navigateToDetail = navigateToDetail)
                } else {
                    Column(
                        modifier = modifier.fillMaxSize().padding(top = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Image(
                            modifier = modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            painter = painterResource(id = R.drawable.placeholder_400px),
                            contentDescription = null
                        )
                        Spacer(modifier = modifier.padding(8.dp))
                        Text(
                            text = stringResource(R.string.mulai_cari_obat),
                            style = MaterialTheme.typography.headlineLarge
                        )

                    }
                }
            }

            is UiState.Error -> {}
        }
    }
}



@Preview
@Composable
fun HomeScreenPreview() {
    MedicifyTheme {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
            KoinApplication(moduleList = { listOf(appModule) }) {
                HomeScreen(
                    navigateToDetail = {},
                    user = null
                )
            }
        }
    }
}
