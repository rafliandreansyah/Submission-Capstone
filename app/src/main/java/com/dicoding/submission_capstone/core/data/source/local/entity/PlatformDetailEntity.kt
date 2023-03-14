package com.dicoding.submission_capstone.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "platform_detail", foreignKeys = [
    ForeignKey(
        entity = DetailGameEntity::class,
        parentColumns = ["detail_game_id"],
        childColumns = ["detail_game_id"],
        onDelete = ForeignKey.CASCADE
    )
])
data class PlatformDetailEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var platformId: Long = 0,
    @ColumnInfo("detail_game_id")
    var detailGameId: Long,
    @ColumnInfo("name")
    var name: String,
    @ColumnInfo("slug")
    var slug: String
)
