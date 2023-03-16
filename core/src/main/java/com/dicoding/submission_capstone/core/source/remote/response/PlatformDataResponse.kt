package com.dicoding.submission_capstone.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class PlatformDataResponse(
    @SerializedName("id")
    var id: Long?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("slug")
    var slug: String?
)