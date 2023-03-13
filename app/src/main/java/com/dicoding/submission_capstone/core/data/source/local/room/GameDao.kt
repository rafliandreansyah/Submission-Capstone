package com.dicoding.submission_capstone.core.data.source.local.room

import androidx.room.*
import com.dicoding.submission_capstone.core.data.source.local.entity.*
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.DetailGameWithPlatformsAndGenresAndDevelopers
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.GameWithPlatformsAndGenres
import com.dicoding.submission_capstone.core.domain.model.DetailGame
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
abstract class GameDao {

    @Query("SELECT * FROM game")
    abstract fun getListGame(): Flowable<List<GameWithPlatformsAndGenres>>

    @Query("SELECT * FROM detail_game WHERE game_detail_id = :gameId")
    abstract fun getDetailGame(gameId: Long): Flowable<DetailGameWithPlatformsAndGenresAndDevelopers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertGame(games: GameEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertListGenre(genres: List<GenreEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertListPlatform(platforms: List<PlatformEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDetailGame(detailGame: DetailGameEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertListDeveloper(developers: List<DeveloperEntity>): Completable

    @Transaction
    fun insertListGame(games: List<GameWithPlatformsAndGenres>): Completable {
        return Completable.fromCallable {
            games.forEach { gameData ->
                insertGame(gameData.game)

                val platforms = gameData.listPlatform.map { platformEntity ->
                    PlatformEntity(
                        platformId = platformEntity.platformId,
                        gameId = gameData.game.gameId,
                        name = platformEntity.name,
                        slug = platformEntity.slug
                    )
                }
                insertListPlatform(platforms)

                val genres = gameData.listGenre.map { genreEntity ->
                    GenreEntity(
                        genreId = genreEntity.genreId,
                        gameId = gameData.game.gameId,
                        name = genreEntity.name,
                        slug = genreEntity.slug
                    )
                }
                insertListGenre(genres)

            }
        }
    }

    @Transaction
    fun insertDetailGame(detailGameEntity: DetailGameEntity,  listDeveloper: List<DeveloperEntity>): Completable {
        return Completable.fromCallable {
            insertDetailGame(detailGameEntity)
        }.andThen {
            val developers = listDeveloper.map { developerEntity ->
                DeveloperEntity(
                    developerId = developerEntity.developerId,
                    gameId = detailGameEntity.detailGameId,
                    gamesCount = developerEntity.gamesCount,
                    imageBackground = developerEntity.imageBackground,
                    name = developerEntity.name,
                    slug = developerEntity.slug
                )
            }
            insertListDeveloper(developers)
        }
    }
}