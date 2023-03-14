package com.dicoding.submission_capstone.core.util

import com.dicoding.submission_capstone.core.data.source.local.entity.*
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.DetailGameWithPlatformsAndGenresAndDevelopers
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.GameWithPlatformsAndGenres
import com.dicoding.submission_capstone.core.data.source.remote.response.PlatformResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.detail_game.DetailGameResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.detail_game.DeveloperResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.games.GameResponse
import com.dicoding.submission_capstone.core.domain.model.DetailGame
import com.dicoding.submission_capstone.core.domain.model.Game
import okhttp3.internal.platform.Platform

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

    fun detailGameResponseToDetailGameEntity(detailGameResponse: DetailGameResponse): DetailGameEntity {
        return DetailGameEntity(
            detailGameId = detailGameResponse.id ?: 0L,
            name = detailGameResponse.name ?: "",
            description = detailGameResponse.descriptionRaw ?: "",
            backgroundImage = detailGameResponse.backgroundImage ?: "",
            rating = detailGameResponse.rating ?: 0.0
        )
    }

    fun developerResponseToDeveloperEntity(listDeveloperResponse: List<DeveloperResponse>): List<DeveloperEntity> {
        return listDeveloperResponse.map {
            DeveloperEntity(
                developerId = it.id ?: 0L,
                gameId = 0L,
                gamesCount = it.gamesCount ?: 0,
                imageBackground = it.imageBackground ?: "",
                slug = it.slug ?: "",
                name = it.name ?: ""
            )
        }
    }

    fun detailGameWithPlatformsAndGenresAndDevelopersToDetailGame(data: DetailGameWithPlatformsAndGenresAndDevelopers): DetailGame {
        return DetailGame(
            id = data.detailGame.detailGameId,
            name = data.detailGame.name,
            description = data.detailGame.description,
            backgroundImage = data.detailGame.backgroundImage,
            rating = data.detailGame.rating,
            developers = data.listDeveloper.map { it.name },
            platForms = data.listPlatform.map { it.name },
            genre = data.listGenre.map { it.name }
        )
    }

    fun gameResponseToGameWithPlatformsAndGenres(listGameResponse: List<GameResponse>): List<GameWithPlatformsAndGenres> {
        return listGameResponse.map {gameResponse ->
            val gameEntity = GameEntity(
                gameId = gameResponse.id ?: 0,
                name = gameResponse.name ?: "",
                backgroundImage = gameResponse.backgroundImage ?: "",
            )
            val platforms = gameResponse.platforms?.map { platform ->
                val platformData = platform.platform
                PlatformEntity(
                    platformId = platformData?.id ?: 0L,
                    gameId = gameEntity.gameId,
                    name = platformData?.name ?: "",
                    slug = platformData?.slug ?: ""
                )
            }

            val genres = gameResponse.genres?.map { genre ->
                GenreEntity(
                    genreId = genre.id ?: 0,
                    gameId = gameEntity.gameId,
                    name = genre.name ?: "",
                    slug = genre.slug ?: ""
                )
            }
            GameWithPlatformsAndGenres(
                game = gameEntity,
                listPlatform = platforms ?: listOf(),
                listGenre = genres ?: listOf()
            )
        }
    }

    fun gameWithPlatformsAndGenresToGame(listGameWithPlatformsAndGenres: List<GameWithPlatformsAndGenres>): List<Game> {
        return listGameWithPlatformsAndGenres.map {data ->
            Game(
                id = data.game.gameId,
                name = data.game.name,
                backgroundImage = data.game.backgroundImage,
                platforms = data.listPlatform.map { it.name },
                genres = data.listGenre.map { it.name }
            )
        }

    }

}