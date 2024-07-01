package com.flickrsearch.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flickrsearch.R
import com.flickrsearch.enums.Status
import com.flickrsearch.models.Item
import com.flickrsearch.models.Resource
import com.flickrsearch.ui.components.CustomOutlinedTextField
import com.flickrsearch.ui.components.ImageItem
import com.flickrsearch.ui.theme.FlickrSearchTheme

/**
 * Composable function that displays search screen with list of photos upon search
 */
@Composable
fun SearchScreen(
    queryText: State<String>,
    onSetQueryText: (query: String) -> Unit,
    photosResponse: Resource<List<Item>>?,
    onItemClick: (it: Item) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomOutlinedTextField(
            title = stringResource(id = R.string.search_photos),
            placeHolder = stringResource(id = R.string.placeholder_photo_name),
            textState = queryText.value,
            onTextChange = onSetQueryText,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
            onSearchDone = { onSetQueryText(queryText.value) }
        )

        Spacer(modifier = Modifier.height(10.dp))
        
        when (photosResponse?.status) {
            Status.LOADING -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Status.SUCCESS -> {
                val photos: List<Item>? = photosResponse.data
                photos?.let {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 100.dp),
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        items(photos) {
                            Surface(
                                tonalElevation = 3.dp,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clearAndSetSemantics {
                                        this.contentDescription =
                                            "${it.title} Photo by ${it.author}"
                                    }
                                    .clickable {
                                        onItemClick(it)
                                    }
                            ) {
                                it.media?.m?.let { imageUrl ->
                                    ImageItem(
                                        imageUrl = imageUrl,
                                        modifier = Modifier.aspectRatio(1f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Status.ERROR -> {
                Box(modifier = Modifier.padding(16.dp)) {
                    photosResponse.message?.let { message ->
                        Text(
                            text = message,
                            style = TextStyle(textAlign = TextAlign.Center)
                        )
                    }
                }
            }

            else -> {
                Box {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen(){
    val queryText = remember { mutableStateOf("") }
    FlickrSearchTheme {
        SearchScreen(
            queryText = queryText,
            onSetQueryText = {},
            photosResponse = null,
            onItemClick = {}
        )
    }
}
