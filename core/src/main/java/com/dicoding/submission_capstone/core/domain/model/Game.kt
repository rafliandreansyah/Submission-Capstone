package com.dicoding.submission_capstone.core.domain.model

data class Game(
    var backgroundImage: String?,
    var id: Long?,
    var name: String?,
    var platforms: List<String>?,
    var genres: List<String>?,
    var isSave: Boolean = false
)