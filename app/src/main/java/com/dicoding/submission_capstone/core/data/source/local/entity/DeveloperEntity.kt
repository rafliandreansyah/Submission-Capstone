package com.dicoding.submission_capstone.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "developer",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["game_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DeveloperEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val developerId: Long = 0,
    @ColumnInfo("game_id")
    val gameId: Long,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("slug")
    val slug: String
)