package com.medicify.app.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.medicify.app.R
import com.medicify.app.ui.theme.MedicifyTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit = {},
) {
    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier.padding(8.dp)) {

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    showClearButton = (focusState.isFocused)
                },
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(
                    text = stringResource(R.string.search_placeholder),
                    fontSize = 14.sp,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
            ),
            shape = RoundedCornerShape(50.dp),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            trailingIcon = {
                AnimatedVisibility(
                    visible = showClearButton,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { onClearClick() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Hapus Search",
                            tint = MaterialTheme.colorScheme.scrim
                        )
                    }
                }
            },
        )
    }
}


@Preview
@Composable
fun SearchBarPreview() {
    MedicifyTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            var query by remember {
                mutableStateOf("")
            }
            SearchBar(
                query = query,
                onQueryChange = { newQuery ->
                    query = newQuery
                },
                onClearClick = { query = "" },
                modifier = Modifier
            )
        }
    }
}