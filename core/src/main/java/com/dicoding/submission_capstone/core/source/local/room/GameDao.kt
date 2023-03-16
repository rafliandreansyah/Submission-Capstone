package com.dicoding.submission_capstone.core.data.source.local.room

import androidx.room.*
import com.dicoding.submission_capstone.core.data.source.local.entity.*
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.GameWithDetailData
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.GameWithPlatformsAndGenres
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface GameDao {

    @Transaction
    @Query("SELECT * FROM game")
    fun getListGame(): Flowable<List<GameWithPlatformsAndGenres>>

    @Query("SELECT * FROM game WHERE is_favorite = 1")
    fun getListFavoriteGame(): Flowable<List<GameWithPlatformsAndGenres>>

    @Transaction
    @Query("SELECT * FROM game WHERE id = :id")
    fun getDetailGame(id: Long): Flowable<GameWithDetailData>

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

    @Update
    fun updateFavoriteGame(gameEntity: GameEntity): Completable

    @Transaction
    fun insertListGame(games: List<GameWithPlatformsAndGenres>) {

        val gamesData: ArrayList<GameEntity> = ArrayList()
        val platforms: ArrayList<PlatformEntity> = ArrayList()
        val genres: ArrayList<GenreEntity> = ArrayList()


        games.forEach { gameData ->
            gamesData.add(gameData.game)
            gameData.listGenre.forEach {
                val genreData = GenreEntity(
                    gameId = gameData.game.id,
                    name = it.name,
                    slug = it.slug
                )
                genres.add(genreData)
            }
            gameData.listPlatform.forEach {
                val platformData = PlatformEntity(
                    gameId = gameData.game.id,
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
    fun insertDetailGame(gameDetail: GameWithDetailData) {

        gameDetail.detailGame?.let { insertDetailGame(it) }

        insertListDeveloper(gameDetail.listDeveloper)

    }
}