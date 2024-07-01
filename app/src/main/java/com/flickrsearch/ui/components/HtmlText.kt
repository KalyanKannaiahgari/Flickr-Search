package com.flickrsearch.ui.components

import android.text.method.LinkMovementMethod
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

/**
 * Composable function to display HTML content in a text view.
 *
 * @param html HTML string to be displayed.
 * @param textColor color for the html string.
 */
@Composable
fun HtmlText(html: String, textColor: Color) {
    val context = LocalContext.current
    AndroidView(
        factory = {
            AppCompatTextView(context).apply {
                text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
                setTextColor(textColor.toArgb())
                movementMethod = LinkMovementMethod.getInstance() // Enable link clicking
            }
        },
        modifier = Modifier.semantics { this.contentDescription = "" }
    )
}
