package com.dicoding.submission_capstone.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail_game")
data class DetailGameEntity(
    @PrimaryKey
    @ColumnInfo("detail_game_id")
    val detailGameId: Long,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("background_image_url")
    val backgroundImage: String,
    @ColumnInfo("rating")
    val rating: Double,
)