# Flickr Image Search

Flick Image Search is an app that allows users to search for images on Flickr. The app is built
using modern development tools and libraries such as Kotlin, Jetpack Compose, Retrofit, and Dagger Hit.

## Table of Contents
- Features
- Uses
- Architecture
- Testing
- Accessibility Compliance

## Features

- Search for images on Flickr using keywords
- Display search results in a grid layout
- View detailed information about each selected image
- Error handling and user-friendly messages
- Modern UI using Jetpack Compose
- Accessibility compliant to enhance user experience

## Uses

- Search for Images by entering the keywords in the search bar and clicking the search icon
- The search will display images related to the keywords from Flickr
- Each image will displayed the detailed information about the image in the details screen

## Architecture

### ViewModel
- SearchViewModel manages the search functionality and photo details 
### Repository
- SearchRepository Handles data operations and abstract the data source
### Network
- Retrofit is used for network operations and Dagger Hilt is used for the dependency injection
### UI
- Jetpack Compose is used for building the UI

## Testing

### Details Screen Test
The `DetailsScreenTest` class contains tests to test the `DetailsScreen` composable functions properly:
- **testDetailsScreenContent**: Verifies aht the content of the image is displayed correctly such as title, author, formatted date.
- **testBackButtonPress**: Checks the functionality of the back button press is handled correctly.
- **testLandscapeMode**: Verifies that the landscape mode displays the content accordingly.

### Search Screen Test
The `SearchScreenTest` class contains tests to test the `SearchScreen` composable functions properly:
- **testLoadingState**: Ensures that the loading state is displayed.
- **testSuccessState**: Verifies that the search results are displayed when the data is loaded successfully.
- **testErrorState**: Checks if the error message is displayed in case of an error.

## Accessibility Compliance

Flickr Image Search is providing a enhanced user experience for all user, including those with disabilities.
The app includes the following accessibility features:
- Content Descriptions: All images and interactive components have content descriptions to support screen readers.
- Screen Reader Support: Ensure that all content is accessible.
- Light/Dark mode: Compatible with both light and dark mode settings to improve visibility for users.
- Supports Various Font Sizes: The app supports large and small font scaling, allowing users to adjust the text size.