package com.dicoding.submission_capstone.core.data.source.local.room

import android.util.Log
import androidx.room.*
import com.dicoding.submission_capstone.core.data.source.local.entity.*
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.DetailGameWithPlatformsAndGenresAndDevelopers
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.GameWithPlatformsAndGenres
import com.dicoding.submission_capstone.core.domain.model.DetailGame
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Dao
interface GameDao {

    @Query("SELECT * FROM game")
    fun getListGame(): Flowable<List<GameWithPlatformsAndGenres>>

    @Query("SELECT * FROM detail_game WHERE detail_game_id = :gameId")
    fun getDetailGame(gameId: Long): Flowable<DetailGameWithPlatformsAndGenresAndDevelopers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(games: List<GameEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListGenre(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListPlatform(platforms: List<PlatformEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailGame(detailGame: DetailGameEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListDeveloper(developers: List<DeveloperEntity>)

    @Query("DELETE FROM platform")
    fun deleteAllPlatform()

    @Query("DELETE FROM genre")
    fun deleteAllGenre()

    @Query("DELETE FROM developer")
    fun deleteAllDeveloper()

    @Transaction
    fun insertListGame(games: List<GameWithPlatformsAndGenres>) {

        val gamesData: ArrayList<GameEntity> = ArrayList()
        val platforms: ArrayList<PlatformEntity> = ArrayList()
        val genres: ArrayList<GenreEntity> = ArrayList()


        games.forEach { gameData ->
            gamesData.add(gameData.game)
            gameData.listGenre.forEach {
                val genreData = GenreEntity(
                    gameId = gameData.game.gameId,
                    name = it.name,
                    slug = it.slug
                )
                genres.add(genreData)
            }
            gameData.listPlatform.forEach {
                val platformData = PlatformEntity(
                    gameId = gameData.game.gameId,
                    name = it.name,
                    slug = it.slug
                )
                platforms.add(platformData)
            }


        }

        deleteAllGenre()
        deleteAllPlatform()

        insertGame(gamesData)
        insertListGenre(genres)
        insertListPlatform(platforms)

    }

    @Transaction
    open fun insertDetailGame(
        detailGameEntity: DetailGameEntity,
        listDeveloper: List<DeveloperEntity>
    ) {
        insertDetailGame(detailGameEntity)
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