package com.dicoding.submission_capstone.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "genre",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["game_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GenreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var genreId: Long = 0,
    @ColumnInfo("game_id")
    var gameId: Long,
    @ColumnInfo("name")
    var name: String,
    @ColumnInfo("slug")
    var slug: String
)