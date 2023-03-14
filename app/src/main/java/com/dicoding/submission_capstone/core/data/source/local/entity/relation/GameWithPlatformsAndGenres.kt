package com.dicoding.submission_capstone.core.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.dicoding.submission_capstone.core.data.source.local.entity.GameEntity
import com.dicoding.submission_capstone.core.data.source.local.entity.GenreEntity
import com.dicoding.submission_capstone.core.data.source.local.entity.PlatformEntity

data class GameWithPlatformsAndGenres(
    @Embedded val game: GameEntity,
    @Relation(
        entity = PlatformEntity::class,
        parentColumn = "game_id",
        entityColumn = "game_id"
    )
    val listPlatform: List<PlatformEntity>,
    @Relation(
        entity = GenreEntity::class,
        parentColumn = "game_id",
        entityColumn = "game_id"
    )
    val listGenre: List<GenreEntity>
)