package com.dicoding.submission_capstone.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.submission_capstone.core.data.source.local.entity.DeveloperEntity
import com.dicoding.submission_capstone.core.data.source.local.entity.GameEntity
import com.dicoding.submission_capstone.core.data.source.local.entity.GenreEntity
import com.dicoding.submission_capstone.core.data.source.local.entity.PlatformEntity

@Database(entities = [GameEntity::class, GenreEntity::class, PlatformEntity::class, DeveloperEntity::class], version = 1)
abstract class GameDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao
}