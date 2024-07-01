package com.flickrsearch.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * Composable function to display the image from a URL
 *
 * @param imageUrl URL of the image to be displayed.
 * @param modifier Modifier for the image.
 */
@Composable
fun ImageItem(imageUrl:String, modifier: Modifier = Modifier,) {
    Surface(
        modifier = modifier,
        tonalElevation = 3.dp
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}