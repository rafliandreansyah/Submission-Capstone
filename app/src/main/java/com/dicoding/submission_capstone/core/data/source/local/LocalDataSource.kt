package com.dicoding.submission_capstone.core.data.source.local

import com.dicoding.submission_capstone.core.data.source.local.entity.*
import com.dicoding.submission_capstone.core.data.source.local.entity.relation.GameWithPlatformsAndGenres
import com.dicoding.submission_capstone.core.data.source.local.room.GameDao
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val gameDao: GameDao) {

    fun insertListGameToDatabase(listGame: List<GameWithPlatformsAndGenres>): Completable {
        return Completable.fromCallable {
            gameDao.insertListGame(listGame)
        }
    }
    fun getGames() = gameDao.getListGame()

    fun getDetailGame(id: Long) = gameDao.getDetailGame(id)

    fun insertDetailGameToDatabase(detailGameEntity: DetailGameEntity, listDeveloper: List<DeveloperEntity>): Completable {
        return Completable.fromCallable {
            gameDao.insertDetailGame(detailGameEntity, listDeveloper)
        }
    }

}