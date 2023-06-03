package com.medicify.app.ui.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource

@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) =
    if (LocalInspectionMode.current) {
        painterResource(id = debugPreview)
    } else {
        null
    }


fun String.firstWord(): String {
    val index = this.indexOf(' ')
    return if (index > -1) {
        this.substring(0, index).trim { it <= ' ' }
    } else {
        this
    }
}

@Composable
fun Int.getVectorResource(): ImageVector {
    return ImageVector.vectorResource(id = this)
}

fun copyToClipboard(context: Context, text: String) {
    val clipboardManager =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("text", text)
    clipboardManager.setPrimaryClip(clip)
}