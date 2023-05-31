package com.medicify.app.ui.utils

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource

@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) =
    if (LocalInspectionMode.current) {
        painterResource(id = debugPreview)
    } else {
        null
    }


fun getFirstWord(text: String): String {
    val index = text.indexOf(' ')
    return if (index > -1) {
        text.substring(0, index).trim { it <= ' ' }
    } else {
        text
    }
}