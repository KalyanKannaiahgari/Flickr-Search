package com.flickrsearch

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flickrsearch.enums.Status
import com.flickrsearch.models.Item
import com.flickrsearch.models.Media
import com.flickrsearch.models.Resource
import com.flickrsearch.ui.screens.SearchScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoadingState() {
        val query = mutableStateOf("")
        val photosResponse = Resource(Status.LOADING, null, null)

        composeTestRule.setContent {
            SearchScreen(
                queryText = query,
                onSetQueryText = { query.value = it },
                photosResponse = photosResponse,
                onItemClick = {}
            )
        }

        composeTestRule.onRoot(true).assertIsDisplayed()
    }

    @Test
    fun testSuccessState() {
        val query = mutableStateOf("")
        val items = listOf(
            Item(title = "Sample Photo 1", author = "Author 1", media = Media("https://via.placeholder.com/150")),
            Item(title = "Sample Photo 2", author = "Author 2", media = Media("https://via.placeholder.com/150"))
        )
        val photosResponse = Resource(Status.SUCCESS, items, null)

        composeTestRule.setContent {
            SearchScreen(
                queryText = query,
                onSetQueryText = { query.value = it },
                photosResponse = photosResponse,
                onItemClick = {}
            )
        }

        composeTestRule.onNodeWithTag("SearchResult").assertIsDisplayed()
    }

    @Test
    fun testErrorState() {
        val query = mutableStateOf("")
        val errorMessage = "An error occurred"
        val photosResponse = Resource(Status.ERROR, null, errorMessage)

        composeTestRule.setContent {
            SearchScreen(
                queryText = query,
                onSetQueryText = { query.value = it },
                photosResponse = photosResponse,
                onItemClick = {}
            )
        }

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

}
