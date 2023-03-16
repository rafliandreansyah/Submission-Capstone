package com.dicoding.submission_capstone.core.domain.model

data class DetailGame(
    var id: Long?,
    var name: String?,
    var description: String?,
    var backgroundImage: String?,
    var rating: Double?,
    var developers: List<String>?,
    var platForms: List<String>?,
    var genre: List<String>?,
    var isFavorite: Boolean = false
)