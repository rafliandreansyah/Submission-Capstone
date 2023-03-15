package com.dicoding.submission_capstone.core.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.dicoding.submission_capstone.core.data.source.local.entity.*
import com.dicoding.submission_capstone.core.domain.model.Game

data class GameWithDetailData(
    @Embedded val game: GameEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "game_id"
    )
    val detailGame: DetailGameEntity?,

    @Relation(
        parentColumn = "id",
        entityColumn = "game_id"
    )
    val listPlatform: List<PlatformEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "game_id"
    )
    val listGenre: List<GenreEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "game_id"
    )
    val listDeveloper: List<DeveloperEntity>
)
