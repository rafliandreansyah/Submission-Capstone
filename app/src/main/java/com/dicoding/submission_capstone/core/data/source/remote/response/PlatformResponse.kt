package com.dicoding.submission_capstone.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class PlatformResponse(
    @SerializedName("platform")
    var platform: PlatformDataResponse?,
    @SerializedName("released_at")
    var releasedAt: String?,
    @SerializedName("requirements")
    var requirements: RequirementsResponse?
)