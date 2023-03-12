package com.dicoding.submission_capstone.core.util

import com.dicoding.submission_capstone.core.data.source.remote.response.detail_game.DetailGameResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.games.GameResponse
import com.dicoding.submission_capstone.core.domain.model.DetailGame
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

    fun detailGameResponseToDetailGame(detailGameResponse: DetailGameResponse): DetailGame {
        return DetailGame(
            id = detailGameResponse.id,
            name = detailGameResponse.name,
            description = detailGameResponse.descriptionRaw,
            backgroundImage = detailGameResponse.backgroundImage,
            rating = detailGameResponse.rating,
            developers = detailGameResponse.developers?.map { developer -> developer.name.toString()},
            platForms = detailGameResponse.platforms?.map { platform -> platform.platform?.name.toString() },
            genre = detailGameResponse.genres?.map { genre -> genre.name.toString() }
        )
    }

}