package com.dicoding.submission_capstone.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "developer",
    foreignKeys = [
        ForeignKey(
            entity = DetailGameEntity::class,
            parentColumns = ["detail_game_id"],
            childColumns = ["game_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DeveloperEntity(
    @PrimaryKey
    @ColumnInfo("developer_id")
    var developerId: Long,
    @ColumnInfo("game_id")
    var gameId: Long,
    @ColumnInfo("games_count")
    var gamesCount: Int,
    @ColumnInfo("image_background_url")
    var imageBackground: String,
    @ColumnInfo("name")
    var name: String,
    @ColumnInfo("slug")
    var slug: String
)