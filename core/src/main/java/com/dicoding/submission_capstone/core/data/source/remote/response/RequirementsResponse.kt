package com.dicoding.submission_capstone.core.data.source.remote.response


import com.google.gson.annotations.SerializedName

data class RequirementsResponse(
    @SerializedName("minimum")
    var minimum: String?,
    @SerializedName("recommended")
    var recommended: String?
)