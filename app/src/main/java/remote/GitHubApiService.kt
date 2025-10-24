package com.example.myapplicationmeuprimeiroapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users/{username}/repos")
    suspend fun getRepositories(@Path("username") username: String): List<GitHubRepo>
}