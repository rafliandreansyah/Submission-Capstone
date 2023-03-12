package com.dicoding.submission_capstone.core.domain.model

import com.dicoding.submission_capstone.core.data.source.remote.response.GenreResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.PlatformResponse

data class Game(
    var backgroundImage: String?,
    var id: Long?,
    var name: String?,
    var platforms: List<String>?,
    var genres: List<String>?,
    var isSave: Boolean = false
)