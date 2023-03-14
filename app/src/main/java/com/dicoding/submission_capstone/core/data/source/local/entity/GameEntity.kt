package com.dicoding.submission_capstone.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameEntity (
    @PrimaryKey
    @ColumnInfo(name = "game_id")
    val gameId: Long,
    @ColumnInfo(name = "background_image_url")
    val backgroundImage: String,
    @ColumnInfo(name = "name")
    val name: String
)