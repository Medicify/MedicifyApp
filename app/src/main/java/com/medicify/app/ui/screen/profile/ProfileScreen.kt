package com.medicify.app.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseUser
import com.medicify.app.R
import com.medicify.app.ui.theme.MedicifyTheme
import com.medicify.app.ui.utils.debugPlaceholder

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    user: FirebaseUser?,
    onSignOutClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = modifier
                    .size(150.dp)
                    .clip(CircleShape),
                placeholder = debugPlaceholder(debugPreview = R.drawable.drug_placeholder),
                model = user?.photoUrl,
                contentDescription = stringResource(R.string.foto_profile)
            )
            Spacer(modifier = modifier.padding(8.dp))
            Text(
                text = user?.displayName ?: "Guest",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = user?.email ?: "email@email.com",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = modifier.padding(20.dp))
        SignOutButton(signOut = onSignOutClicked, modifier = modifier)
    }
}

@Composable
private fun SignOutButton(modifier: Modifier, signOut: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(false) }
    Button(modifier = modifier.fillMaxWidth(), onClick = { showWarningDialog = true }) {
        Text(
            text = "Keluar",
            style = MaterialTheme.typography.bodyLarge
        )
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.confirmation)) },
            text = { Text(stringResource(R.string.exit_confirmation), fontSize = 16.sp) },
            dismissButton = {
                Button(
                    onClick = { showWarningDialog = false },
                    content = { Text(text = stringResource(R.string.cancel), fontSize = 14.sp) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            confirmButton = {
                Button(
                    content = { Text(text = stringResource(R.string.logout), fontSize = 14.sp) },
                    onClick = {
                        signOut()
                        showWarningDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {
    MedicifyTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ProfileScreen(user = null, onSignOutClicked = {})
        }
    }
}