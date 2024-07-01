package com.flickrsearch.repository

import com.flickrsearch.api.ApiHelper
import javax.inject.Inject

class SearchRepository
    @Inject
    constructor(private val apiHelper: ApiHelper) {
        suspend fun searchPhotos(query: String) = apiHelper.searchPhotos(query = query)
    }
