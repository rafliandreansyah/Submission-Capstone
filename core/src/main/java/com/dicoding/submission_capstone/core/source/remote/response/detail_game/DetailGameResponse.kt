package com.dicoding.submission_capstone.core.data.source.remote.response.detail_game


import com.dicoding.submission_capstone.core.data.source.remote.response.GenreResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.PlatformResponse
import com.google.gson.annotations.SerializedName

data class DetailGameResponse(
    @SerializedName("achievements_count")
    var achievementsCount: Int?,
    @SerializedName("added")
    var added: Int?,
    @SerializedName("additions_count")
    var additionsCount: Int?,
    @SerializedName("background_image")
    var backgroundImage: String?,
    @SerializedName("background_image_additional")
    var backgroundImageAdditional: String?,
    @SerializedName("clip")
    var clip: Any?,
    @SerializedName("creators_count")
    var creatorsCount: Int?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("description_raw")
    var descriptionRaw: String?,
    @SerializedName("developers")
    var developers: List<DeveloperResponse>?,
    @SerializedName("dominant_color")
    var dominantColor: String?,
    @SerializedName("game_series_count")
    var gameSeriesCount: Int?,
    @SerializedName("genres")
    var genres: List<GenreResponse>?,
    @SerializedName("id")
    var id: Long?,
    @SerializedName("movies_count")
    var moviesCount: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("name_original")
    var nameOriginal: String?,
    @SerializedName("parent_achievements_count")
    var parentAchievementsCount: Int?,
    @SerializedName("parents_count")
    var parentsCount: Int?,
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
    @SerializedName("reddit_count")
    var redditCount: Int?,
    @SerializedName("reddit_description")
    var redditDescription: String?,
    @SerializedName("reddit_logo")
    var redditLogo: String?,
    @SerializedName("reddit_name")
    var redditName: String?,
    @SerializedName("reddit_url")
    var redditUrl: String?,
    @SerializedName("released")
    var released: String?,
    @SerializedName("reviews_count")
    var reviewsCount: Int?,
    @SerializedName("reviews_text_count")
    var reviewsTextCount: Int?,
    @SerializedName("saturated_color")
    var saturatedColor: String?,
    @SerializedName("screenshots_count")
    var screenshotsCount: Int?,
    @SerializedName("slug")
    var slug: String?,
    @SerializedName("suggestions_count")
    var suggestionsCount: Int?,
    @SerializedName("tba")
    var tba: Boolean?,
    @SerializedName("twitch_count")
    var twitchCount: Int?,
    @SerializedName("updated")
    var updated: String?,
    @SerializedName("website")
    var website: String?,
    @SerializedName("youtube_count")
    var youtubeCount: Int?
)