package com.dicoding.submission_capstone.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "platform", foreignKeys = [
    ForeignKey(
        entity = GameEntity::class,
        parentColumns = ["game_id"],
        childColumns = ["game_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class PlatformEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var platformId: Long = 0,
    @ColumnInfo("game_id")
    var gameId: Long,
    @ColumnInfo("name")
    var name: String,
    @ColumnInfo("slug")
    var slug: String
)
