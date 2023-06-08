package com.medicify.app.data.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ExpandableItem(
    val title: String,
    val content: String?,
    var isExpanded: MutableState<Boolean> = mutableStateOf(false)
)
