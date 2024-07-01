package com.flickrsearch.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flickrsearch.ui.screens.DetailsScreen
import com.flickrsearch.ui.screens.SearchScreen
import com.flickrsearch.viewmodel.SearchViewModel

@Composable
fun NavigationSearch(
    navController: NavHostController = rememberNavController(),
    searchViewModel: SearchViewModel
) {
    NavHost(navController, startDestination = Screen.Search.route) {
        composable(Screen.Search.route) {
            val photosResponse by searchViewModel.photosResponse.observeAsState()
            val queryText = searchViewModel.queryText
            SearchScreen(
                photosResponse = photosResponse,
                queryText = queryText,
                onSetQueryText = {
                    searchViewModel.setQueryText(it)
                },
                onItemClick = {
                    searchViewModel.setPhotoItem(it)
                    navController.navigate(Screen.Detail.route)
                }
            )
        }

        composable(
            Screen.Detail.route,
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            },
            exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(500)
                )
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(500)
                )
            }
        ) {
            val photoDetails by searchViewModel.photoDetails.observeAsState()
            DetailsScreen(
                photoDetails = photoDetails,
                onBackPress = { navController.popBackStack() }
            )
        }
    }
}
