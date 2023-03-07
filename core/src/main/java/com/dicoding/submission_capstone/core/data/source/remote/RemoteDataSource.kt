package com.dicoding.submission_capstone.core.data.source.remote

import com.dicoding.submission_capstone.core.data.source.remote.network.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(val apiService: ApiService) {

}