package com.dicoding.submission_capstone.core.data.source.local.room

import androidx.room.*
import com.dicoding.submission_capstone.core.data.source.local.entity.*
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.DetailGameWithPlatformsAndGenresAndDevelopers
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.GameWithPlatformsAndGenres
import io.reactivex.Flowable

@Dao
interface GameDao {

    @Transaction
    @Query("SELECT * FROM game")
    fun getListGame(): Flowable<List<GameWithPlatformsAndGenres>>

    @Transaction
    @Query("SELECT * FROM detail_game WHERE detail_game_id = :gameId")
    fun getDetailGame(gameId: Int): Flowable<DetailGameWithPlatformsAndGenresAndDevelopers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(games: List<GameEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListGenre(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListPlatform(platforms: List<PlatformEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailGame(detailGame: DetailGameEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListDeveloperDetail(developers: List<DeveloperDetailEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListGenreDetail(developers: List<GenreDetailEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListPlatformDetail(developers: List<PlatformDetailEntity>)

    @Query("DELETE FROM platform")
    fun deleteAllPlatform()

    @Query("DELETE FROM genre")
    fun deleteAllGenre()

    @Query("DELETE FROM developer_detail")
    fun deleteAllDeveloperDetail()

    @Query("DELETE FROM genre_detail")
    fun deleteAllGenreDetail()

    @Query("DELETE FROM platform_detail")
    fun deleteAllPlatformDetail()

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
    fun insertDetailGameTransaction(
        detailGame :DetailGameWithPlatformsAndGenresAndDevelopers
    ) {

        deleteAllDeveloperDetail()
        deleteAllPlatformDetail()
        deleteAllGenreDetail()

        insertDetailGame(detailGame.detailGame)

        val developersDetail = detailGame.listDeveloper.map { data ->
            DeveloperDetailEntity(
                detailGameId = detailGame.detailGame.detailGameId,
                gamesCount = data.gamesCount,
                imageBackground = data.imageBackground,
                name = data.name,
                slug = data.slug
            )
        }
        insertListDeveloperDetail(developersDetail)

        val genresDetail = detailGame.listGenre.map { data ->
            GenreDetailEntity(
                detailGameId = detailGame.detailGame.detailGameId,
                name = data.name,
                slug = data.slug
            )
        }
        insertListGenreDetail(genresDetail)

        val platformDetail = detailGame.listPlatform.map { data ->
            PlatformDetailEntity(
                detailGameId = detailGame.detailGame.detailGameId,
                name = data.name,
                slug = data.slug
            )
        }
        insertListPlatformDetail(platformDetail)


    }
}