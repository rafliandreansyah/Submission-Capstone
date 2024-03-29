package com.dicoding.submission_capstone.core.data.source.remote.network

import com.dicoding.submission_capstone.core.app.API_KEY
import com.dicoding.submission_capstone.core.data.source.remote.response.detail_game.DetailGameResponse
import com.dicoding.submission_capstone.core.data.source.remote.response.games.ListGameResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/api/games")
    fun getGames(@Query("key") key: String? = API_KEY): Flowable<ListGameResponse>

    @GET("/api/games/{id}")
    fun getDetailGame(@Path("id") id: Long, @Query("key") key: String? = API_KEY): Flowable<DetailGameResponse>
}