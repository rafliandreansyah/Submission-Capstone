package com.dicoding.submission_capstone.core.util

import com.dicoding.submission_capstone.core.data.source.local.entity.*
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.GameWithDetailData
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.GameWithPlatformsAndGenres
import com.dicoding.submission_capstone.core.data.source.remote.response.detail_game.DetailGameResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.games.GameResponse
import com.dicoding.submission_capstone.core.domain.model.DetailGame
import com.dicoding.submission_capstone.core.domain.model.Game

object DataMapper {

    fun detailGameResponseToGameWithDetailData(detailGameResponse: DetailGameResponse): GameWithDetailData {

        return GameWithDetailData(
            game = GameEntity(
                id = detailGameResponse.id ?: 0,
                name = detailGameResponse.name ?: "",
                backgroundImage = detailGameResponse.backgroundImage ?: "",
            ),
            detailGame = DetailGameEntity(
                gameId = detailGameResponse.id ?: 0,
                description = detailGameResponse.descriptionRaw ?: "",
                rating = detailGameResponse.rating ?: 0.0
            ),
            listPlatform = detailGameResponse.platforms?.map {
                PlatformEntity(
                    gameId = detailGameResponse.id ?: 0,
                    name = it.platform?.name ?: "",
                    slug = it.platform?.slug ?: "",
                )
            } ?: listOf(),
            listGenre = detailGameResponse.genres?.map {
                GenreEntity(
                    gameId = detailGameResponse.id ?: 0,
                    name = it.name ?: "",
                    slug = it.slug ?: ""
                )
            } ?: listOf(),
            listDeveloper = detailGameResponse.developers?.map {
                DeveloperEntity(
                    gameId = detailGameResponse.id ?: 0,
                    name = it.name ?: "",
                    slug = it.slug ?: ""
                )
            } ?: listOf()
        )
    }

    fun gameWithDetailDataToDetailGame(dataDetail: GameWithDetailData): DetailGame {
        return DetailGame(
            id = dataDetail.game.id,
            name = dataDetail.game.name,
            description = dataDetail.detailGame?.description,
            backgroundImage = dataDetail.game.backgroundImage,
            rating = dataDetail.detailGame?.rating,
            developers = dataDetail.listDeveloper.map { it.name },
            platForms = dataDetail.listPlatform.map { it.name },
            genre = dataDetail.listGenre.map { it.name },
            isFavorite = dataDetail.game.isFavorite
        )
    }

    fun gameResponseToGameWithPlatformsAndGenres(listGameResponse: List<GameResponse>): List<GameWithPlatformsAndGenres> {
        return listGameResponse.map {gameResponse ->
            val gameEntity = GameEntity(
                id = gameResponse.id ?: 0,
                name = gameResponse.name ?: "",
                backgroundImage = gameResponse.backgroundImage ?: "",
            )
            val platforms = gameResponse.platforms?.map { platform ->
                val platformData = platform.platform
                PlatformEntity(
                    platformId = platformData?.id ?: 0L,
                    gameId = gameEntity.id,
                    name = platformData?.name ?: "",
                    slug = platformData?.slug ?: ""
                )
            }

            val genres = gameResponse.genres?.map { genre ->
                GenreEntity(
                    genreId = genre.id ?: 0,
                    gameId = gameEntity.id,
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
                id = data.game.id,
                name = data.game.name,
                backgroundImage = data.game.backgroundImage,
                platforms = data.listPlatform.map { it.name },
                genres = data.listGenre.map { it.name }
            )
        }

    }

    fun detailGameToGameEntity(detailGame: DetailGame): GameEntity {
        return GameEntity(
            id = detailGame.id ?: 0,
            backgroundImage = detailGame.backgroundImage ?: "",
            name = detailGame.name ?: "",
            isFavorite = detailGame.isFavorite
        )
    }

}