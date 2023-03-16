package com.dicoding.submission_capstone.core.data.source.remote.response.games


import com.google.gson.annotations.SerializedName

data class ListGameResponse(
    @SerializedName("count")
    var count: Int?,
    @SerializedName("next")
    var next: String?,
    @SerializedName("previous")
    var previous: String?,
    @SerializedName("results")
    var results: List<GameResponse>?
)