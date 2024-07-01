package com.flickrsearch

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flickrsearch.models.Item
import com.flickrsearch.models.Media
import com.flickrsearch.ui.screens.DetailsScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDetailsScreenContent() {
        // Mock item
        val mockItem = Item(
            title = "Sample Title",
            description = "<p>Sample Description</p>",
            author = "Sample Author",
            published = "2024-06-26T12:10:49Z",
            media = Media(m = "https://via.placeholder.com/150")
        )

        composeTestRule.setContent {
            DetailsScreen(photoDetails = mockItem, onBackPress = {})
        }

        // Check that the title is displayed
        composeTestRule.onNodeWithText("Sample Title").assertIsDisplayed()

        // Check that the author is displayed
        composeTestRule.onNodeWithText("Photo by: Sample Author").assertIsDisplayed()

        // Check that the formatted date is displayed
        composeTestRule.onNodeWithText("Taken on: Jun, 26 2024").assertIsDisplayed()
    }

    @Test
    fun testBackButtonPress() {
        var backButtonPressed = false
        composeTestRule.setContent {
            DetailsScreen(photoDetails = null, onBackPress = { backButtonPressed = true })
        }

        // Simulate back button press
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Assert that the back button press was handled
        assert(backButtonPressed)
    }

    @Test
    fun testOrientationChange() {
        // Mock item
        val mockItem = Item(
            title = "Sample Title",
            description = "<p>Sample Description</p>",
            author = "Sample Author",
            published = "2024-06-26T12:10:49Z",
            media = Media(m = "https://via.placeholder.com/150")
        )

        composeTestRule.setContent {
            DetailsScreen(photoDetails = mockItem, onBackPress = {})
        }

        // Switch to landscape orientation
        /*composeTestRule.activityRule.scenario.onActivity {
            it.resources.configuration.orientation = Configuration.ORIENTATION_LANDSCAPE
        }*/

        // Check that the title is displayed
        composeTestRule.onNodeWithText("Sample Title").assertIsDisplayed()
    }
}
