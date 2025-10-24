package com.example.myapplicationmeuprimeiroapp.data.remote

data class GitHubRepo(
    val id: Int,
    val name: String,
    val description: String?,
    val html_url: String,
    val language: String?
)