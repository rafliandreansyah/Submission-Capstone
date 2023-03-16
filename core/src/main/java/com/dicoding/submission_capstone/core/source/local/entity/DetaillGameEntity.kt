package com.dicoding.submission_capstone.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "detail_game",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["game_id"]
        )
    ]
)
data class DetailGameEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Long = 0,
    @ColumnInfo("game_id")
    val gameId: Long,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("rating")
    val rating: Double,
)