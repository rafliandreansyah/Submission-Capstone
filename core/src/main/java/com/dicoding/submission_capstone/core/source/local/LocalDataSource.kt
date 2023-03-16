package com.dicoding.submission_capstone.core.data.source.local

import com.dicoding.submission_capstone.core.data.source.local.entity.GameEntity
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.GameWithDetailData
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.GameWithPlatformsAndGenres
import com.dicoding.submission_capstone.core.data.source.local.room.GameDao
import io.reactivex.Completable
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val gameDao: GameDao) {

    fun insertListGameToDatabase(listGame: List<GameWithPlatformsAndGenres>): Completable {
        return Completable.fromCallable {
            gameDao.insertListGame(listGame)
        }
    }
    fun getGames() = gameDao.getListGame()

    fun getDetailGame(id: Long) = gameDao.getDetailGame(id)

    fun insertDetailGameToDatabase(detailGame: GameWithDetailData): Completable {
        return Completable.fromCallable {
            gameDao.insertDetailGame(detailGame)
        }
    }

    fun updateFavoriteGame(gameEntity: GameEntity): Completable {
        return gameDao.updateFavoriteGame(gameEntity)
    }

    fun getListFavoriteGame() = gameDao.getListFavoriteGame()
}