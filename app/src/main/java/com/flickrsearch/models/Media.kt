package com.flickrsearch.models

import com.google.gson.annotations.SerializedName

data class Media(
    @SerializedName("m") var m: String? = null
)
