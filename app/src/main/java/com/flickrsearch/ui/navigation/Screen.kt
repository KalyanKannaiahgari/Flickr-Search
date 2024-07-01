package com.flickrsearch.ui.navigation

sealed class Screen(var route: String, var title: String) {
    data object Search : Screen("search", "Search")
    data object Detail : Screen("detail", "Detail")
}
