package com.dicoding.submission_capstone.core.util

import com.dicoding.submission_capstone.core.data.source.local.entity.*
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.DetailGameWithPlatformsAndGenresAndDevelopers
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.GameWithPlatformsAndGenres
import com.dicoding.submission_capstone.core.data.source.remote.response.detail_game.DetailGameResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.detail_game.DeveloperResponse
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

    fun detailGameResponseToDetailGameWithPlatformsAndGenresAndDevelopers(detailGameResponse: DetailGameResponse): DetailGameWithPlatformsAndGenresAndDevelopers {
        val listGenreDetail = detailGameResponse.genres?.map {
            GenreDetailEntity(
                genreId = it.id ?: 0L,
                detailGameId = 0L,
                name = it.name ?: "",
                slug = it.slug ?: ""
            )
        }
        val listPlatformDetail = detailGameResponse.platforms?.map {
            PlatformDetailEntity(
                platformId = it.platform?.id ?: 0L,
                detailGameId = 0L,
                name = it.platform?.name ?: "",
                slug = it.platform?.slug ?: ""
            )
        }
        val listDeveloperDetail = detailGameResponse.developers?.map {
            DeveloperDetailEntity(
                developerId = it.id ?: 0L,
                detailGameId = 0L,
                gamesCount = it.gamesCount ?: 0,
                imageBackground = it.imageBackground ?: "",
                name = it.name ?: "",
                slug = it.slug ?: ""
            )
        }
        return DetailGameWithPlatformsAndGenresAndDevelopers(
            detailGame = DetailGameEntity(
                detailGameId = detailGameResponse.id ?: 0L,
                name = detailGameResponse.name ?: "",
                description = detailGameResponse.description ?: "",
                backgroundImage = detailGameResponse.backgroundImage ?: "",
                rating = detailGameResponse.rating ?: 0.0
            ),
            listDeveloper = listDeveloperDetail ?: listOf(),
            listPlatform = listPlatformDetail ?: listOf(),
            listGenre = listGenreDetail ?: listOf()

        )
    }

    fun developerResponseToDeveloperEntity(listDeveloperResponse: List<DeveloperResponse>): List<DeveloperDetailEntity> {
        return listDeveloperResponse.map {
            DeveloperDetailEntity(
                developerId = it.id ?: 0L,
                detailGameId = 0L,
                gamesCount = it.gamesCount ?: 0,
                imageBackground = it.imageBackground ?: "",
                slug = it.slug ?: "",
                name = it.name ?: ""
            )
        }
    }

    fun detailGameWithPlatformsAndGenresAndDevelopersToDetailGame(dataDetail: DetailGameWithPlatformsAndGenresAndDevelopers): DetailGame {
        return DetailGame(
            id = dataDetail.detailGame.detailGameId,
            name = dataDetail.detailGame.name,
            description = dataDetail.detailGame.description,
            backgroundImage = dataDetail.detailGame.backgroundImage,
            rating = dataDetail.detailGame.rating,
            developers = dataDetail.listDeveloper.map { it.name },
            platForms = dataDetail.listPlatform.map { it.name },
            genre = dataDetail.listGenre.map { it.name }
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