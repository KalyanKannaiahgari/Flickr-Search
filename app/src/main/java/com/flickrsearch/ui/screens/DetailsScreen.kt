package com.flickrsearch.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flickrsearch.R
import com.flickrsearch.models.Item
import com.flickrsearch.ui.components.HtmlText
import com.flickrsearch.ui.components.ImageItem
import com.flickrsearch.ui.theme.DarkGrey
import com.flickrsearch.ui.theme.FlickrSearchTheme
import com.flickrsearch.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Composable function to display the image details screen and it's content
 *
 * @param photoDetails Details of the photo displayed.
 * @param onBackPress Callback function to be invoked when the back button is used.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(photoDetails: Item?, onBackPress: () -> Unit) {
    val configuration = LocalConfiguration.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.clickable {
                                onBackPress()
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.details_text),
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                },
                actions = {}
            )
        },
    ) { paddingValues ->
        photoDetails?.let {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
            ) {
                when (configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        LandscapeMode(it)
                    }

                    else -> {
                        PortraitMode(it)
                    }
                }
            }
        }
    }
}

/**
 * Composable function to display the images in a landscape layout
 */
@Composable
fun LandscapeMode(item: Item) {
    Row {
        item.media?.m?.let { imageUrl ->
            Box(
                modifier = Modifier.weight(.5f),
                contentAlignment = Alignment.BottomEnd
            ) {
                ImageItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clearAndSetSemantics { this.contentDescription = "" },
                    imageUrl = imageUrl
                )
                item.description?.let { description -> 
                    getImageDimensions(description) }?.let { dimensions ->
                    Box(
                        modifier = Modifier
                            .background(color = Color.Black.copy(alpha = .5f))
                            .clearAndSetSemantics {
                                this.contentDescription =
                                    "${dimensions.first} by ${dimensions.second} Photo"
                            }
                    ) {
                        Text(text = "${dimensions.first} x ${dimensions.second}")
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(.5f)
                .verticalScroll(state = rememberScrollState(0)),
            verticalArrangement = Arrangement.Top
        ) {
            item.title?.let { title ->
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Start,
                    fontSize = 22.sp
                )
            }

            item.description?.let { description ->
                HtmlText(
                    html = description,
                    textColor = if (isSystemInDarkTheme()) White else DarkGrey
                )
            }

            item.author?.let { author ->
                Text(
                    text = stringResource(id = R.string.photo_by_author, author),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    style = TextStyle(fontWeight = FontWeight.Normal),
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp
                )
            }

            getFormattedDate(item.published)?.let { publishedDate ->
                Text(
                    text = stringResource(id = R.string.taken_date, publishedDate),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    style = TextStyle(fontWeight = FontWeight.Normal),
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp
                )
            }
        }
    }
}

/**
 * Composable function to display the images in a portrait layout
 */
@Composable
fun PortraitMode(item: Item) {
    Column(
        modifier = Modifier.verticalScroll(state = rememberScrollState(0)),
        verticalArrangement = Arrangement.Top
    ) {
        item.media?.m?.let { imageUrl ->
            Box(contentAlignment = Alignment.BottomEnd) {
                ImageItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    imageUrl = imageUrl
                )

                item.description?.let { description -> 
                    getImageDimensions(description) }?.let { dimensions ->
                    Box(
                        modifier = Modifier
                            .background(color = Color.Black.copy(alpha = .5f))
                            .clearAndSetSemantics {
                                this.contentDescription =
                                    "${dimensions.first} by ${dimensions.second} Photo"
                            }
                    ) {
                        Text(text = "${dimensions.first} x ${dimensions.second}")
                    }
                }
            }
        }

        item.title?.let { title ->
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                style = TextStyle(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Start,
                fontSize = 22.sp
            )
        }

        item.description?.let { description ->
            HtmlText(
                html = description,
                textColor = if (isSystemInDarkTheme()) White else DarkGrey
            )
        }

        item.author?.let { author ->
            Text(
                text = stringResource(id = R.string.photo_by_author, author),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                style = TextStyle(fontWeight = FontWeight.Normal),
                textAlign = TextAlign.Start,
                fontSize = 16.sp
            )
        }

        getFormattedDate(item.published)?.let { publishedDate ->
            Text(
                text = stringResource(id = R.string.taken_date, publishedDate),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                style = TextStyle(fontWeight = FontWeight.Normal),
                textAlign = TextAlign.Start,
                fontSize = 16.sp
            )
        }
    }
}

/**
 * Function to convert date string
 */
fun getFormattedDate(
    inputDateString: String?,
    inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'",
    outputFormat: String = "MMM, dd yyyy",
): String? =
    try {
        val inputDateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
        val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
        val date: Date? = inputDateString?.let { inputDateFormat.parse(it) }
        date?.let { outputDateFormat.format(it) }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

/**
 * Function to extract image dimensions from an HTML string
 */
fun getImageDimensions(html: String): Pair<Int,Int>? {
    val pattern = Regex("""<img[^>]+width="(\d+)"[^>]+height="(\d+)"""")
    val matchResult = pattern.find(html)

    return matchResult?.let {
        val width = it.groupValues[1].toInt()
        val height = it.groupValues[2].toInt()
       Pair(width,height)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsScreen(){
    FlickrSearchTheme {
        DetailsScreen(
            photoDetails = Item(
                title = "Porcupine",
                link = ""
            ),
            onBackPress = {}
        )
    }
}
