package com.dicoding.submission_capstone.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "developer_detail",
    foreignKeys = [
        ForeignKey(
            entity = DetailGameEntity::class,
            parentColumns = ["detail_game_id"],
            childColumns = ["detail_game_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DeveloperDetailEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("developer_id")
    var developerId: Long = 0,
    @ColumnInfo("detail_game_id")
    var detailGameId: Long,
    @ColumnInfo("games_count")
    var gamesCount: Int,
    @ColumnInfo("image_background_url")
    var imageBackground: String,
    @ColumnInfo("name")
    var name: String,
    @ColumnInfo("slug")
    var slug: String
)