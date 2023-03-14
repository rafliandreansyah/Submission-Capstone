package com.dicoding.submission_capstone.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.submission_capstone.core.data.source.local.entity.*

@Database(entities = [
    GameEntity::class,
    DetailGameEntity::class,
    GenreEntity::class,
    PlatformEntity::class,
    DeveloperDetailEntity::class,
    GenreDetailEntity::class,
    PlatformDetailEntity::class
                     ], version = 1)
abstract class GameDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao
}