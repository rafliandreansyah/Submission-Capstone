package com.dicoding.submission_capstone.core.util

import com.dicoding.submission_capstone.core.data.source.remote.response.games.GameResponse
import com.dicoding.submission_capstone.core.domain.model.Game

object DataMapper {

    fun gameResponseToGameData(gameResponse: GameResponse): Game {
        return Game(
            id = gameResponse.id,
            name = gameResponse.name,
            platforms = gameResponse.platforms?.map { it.platform?.slug.toString() },
            genres = gameResponse.genres?.map { it.name.toString() },
            backgroundImage = gameResponse.backgroundImage
        )
    }

}