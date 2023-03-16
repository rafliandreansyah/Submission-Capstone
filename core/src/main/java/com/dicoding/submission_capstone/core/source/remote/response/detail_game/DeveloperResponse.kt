package com.dicoding.submission_capstone.core.data.source.remote.response.detail_game


import com.google.gson.annotations.SerializedName

data class DeveloperResponse(
    @SerializedName("games_count")
    var gamesCount: Int?,
    @SerializedName("id")
    var id: Long?,
    @SerializedName("image_background")
    var imageBackground: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("slug")
    var slug: String?
)