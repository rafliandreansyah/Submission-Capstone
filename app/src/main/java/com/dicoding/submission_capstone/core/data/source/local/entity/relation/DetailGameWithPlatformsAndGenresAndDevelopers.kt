package com.dicoding.submission_capstone.core.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.dicoding.submission_capstone.core.data.source.local.entity.*

data class DetailGameWithPlatformsAndGenresAndDevelopers(
    @Embedded val detailGame: DetailGameEntity,
    @Relation(
        entity = DeveloperDetailEntity::class,
        parentColumn = "detail_game_id",
        entityColumn = "detail_game_id",
    )
    val listDeveloper: List<DeveloperDetailEntity>,
    @Relation(
        entity = GenreDetailEntity::class,
        parentColumn = "detail_game_id",
        entityColumn = "detail_game_id",
    )
    val listGenre: List<GenreDetailEntity>,
    @Relation(
        entity = PlatformDetailEntity::class,
        parentColumn = "detail_game_id",
        entityColumn = "detail_game_id",
    )
    val listPlatform: List<PlatformDetailEntity>
)