package com.dicoding.submission_capstone.core.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.dicoding.submission_capstone.core.data.source.local.entity.DetailGameEntity
import com.dicoding.submission_capstone.core.data.source.local.entity.DeveloperEntity
import com.dicoding.submission_capstone.core.data.source.local.entity.GenreEntity
import com.dicoding.submission_capstone.core.domain.model.DetailGame

data class DetailGameWithPlatformsAndGenresAndDevelopers(
    @Embedded val detailGame: DetailGameEntity,
    @Relation(
        parentColumn = "detail_game_id",
        entityColumn = "game_id",
    )
    val listDeveloper: List<DeveloperEntity>,
    @Relation(
        parentColumn = "detail_game_id",
        entityColumn = "game_id",
    )
    val listGenre: List<GenreEntity>,
    @Relation(
        parentColumn = "detail_game_id",
        entityColumn = "game_id",
    )
    val listPlatform: List<DeveloperEntity>
)