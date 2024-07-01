package com.flickrsearch.api

import com.flickrsearch.models.PhotosResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl
    @Inject
    constructor(private val apiService: ApiService) : ApiHelper {
        override suspend fun searchPhotos(query: String): Response<PhotosResponse> = apiService.searchPhotos(query = query)
    }
