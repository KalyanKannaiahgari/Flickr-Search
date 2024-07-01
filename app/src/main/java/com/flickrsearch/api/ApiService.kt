package com.flickrsearch.api

import com.flickrsearch.data.APIEndPoint
import com.flickrsearch.data.Constants
import com.flickrsearch.models.PhotosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(APIEndPoint.PATH_SEARCH_PHOTOS)
    suspend fun searchPhotos(
        @Query(Constants.PARAM_SEARCH_TAGS) query: String
    ): Response<PhotosResponse>
}
