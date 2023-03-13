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
    abstract fun getListGame(): Flowable<List<GameWithPlatformsAndGenres>>

    @Query("SELECT * FROM detail_game WHERE detail_game_id = :gameId")
    abstract fun getDetailGame(gameId: Long): Flowable<DetailGameWithPlatformsAndGenresAndDevelopers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(games: List<GameEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListGenre(genres: List<GenreEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListPlatform(platforms: List<PlatformEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailGame(detailGame: DetailGameEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListDeveloper(developers: List<DeveloperEntity>): Completable

    @Query("DELETE FROM platform")
    fun deleteAllPlatform(): Completable

    @Query("DELETE FROM genre")
    abstract fun deleteAllGenre(): Completable

    @Query("DELETE FROM developer")
    abstract fun deleteAllDeveloper(): Completable

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
            /*insertGame(gameData.game).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .andThen {
                    val platforms = gameData.listPlatform.map { platformEntity ->
                        PlatformEntity(
                            gameId = gameData.game.gameId,
                            name = platformEntity.name,
                            slug = platformEntity.slug
                        )
                    }
                    insertListPlatform(platforms).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .andThen {
                            val genres = gameData.listGenre.map { genreEntity ->
                                GenreEntity(
                                    gameId = gameData.game.gameId,
                                    name = genreEntity.name,
                                    slug = genreEntity.slug
                                )
                            }
                            insertListGenre(genres).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    Log.d("GAME DAO GENRE", "Sukses")
                                }, {
                                    it.printStackTrace()
                                    Log.e("GAME DAO GENRE", "${it.message}")
                                })
                        }
                        .subscribe({
                            Log.d("GAME DAO PLATFORM", "Sukses")
                        }, {
                            it.printStackTrace()
                            Log.e("GAME DAO PLATFORM", "${it.message}")
                        })
                }
                .subscribe({
                    Log.d("GAME DAO", "Sukses")

                }, {
                    it.printStackTrace()
                    Log.e("GAME DAO", "${it.message}")
                })*/


        }

        deleteAllGenre()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .andThen {
                deleteAllPlatform()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .andThen {
                        insertGame(gamesData)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .andThen {
                                insertListGenre(genres)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                        Log.d("Genres", "Sukses")
                                    }, {
                                        Log.e("Genres", "Gagal")
                                    })

                                insertListPlatform(
                                    platforms
                                ).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                        Log.d("Platform", "Sukses")
                                    }, {
                                        Log.e("Platform", "Gagal")
                                    })
                            }
                            .subscribe({
                                Log.d("Game", "Sukses")
                            }, {
                                Log.e("Game", "Gagal")
                            })
                    }
                    .subscribe()
            }.subscribe()

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