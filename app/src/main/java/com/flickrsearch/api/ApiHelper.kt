package com.flickrsearch.api

import com.flickrsearch.models.PhotosResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun searchPhotos(query: String): Response<PhotosResponse>
}