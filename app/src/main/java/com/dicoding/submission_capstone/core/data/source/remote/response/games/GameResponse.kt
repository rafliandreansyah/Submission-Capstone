package com.dicoding.submission_capstone.core.data.source.remote.response.games


import com.dicoding.submission_capstone.core.data.source.remote.response.GenreResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.PlatformResponse
import com.google.gson.annotations.SerializedName

data class GameResponse(
    @SerializedName("added")
    var added: Int?,
    @SerializedName("background_image")
    var backgroundImage: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("metacritic")
    var metacritic: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("platforms")
    var platforms: List<PlatformResponse>?,
    @SerializedName("playtime")
    var playtime: Int?,
    @SerializedName("rating")
    var rating: Double?,
    @SerializedName("rating_top")
    var ratingTop: Int?,
    @SerializedName("ratings_count")
    var ratingsCount: Int?,
    @SerializedName("released")
    var released: String?,
    @SerializedName("reviews_text_count")
    var reviewsTextCount: String?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("suggestions_count")
    var suggestionsCount: Int?,
    @SerializedName("tba")
    var tba: Boolean?,
    @SerializedName("updated")
    var updated: String?,
    @SerializedName("genres")
    var genres: List<GenreResponse>?
)