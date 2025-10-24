package com.example.myapplicationmeuprimeiroapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String?,
    val html_url: String,
    val language: String?
)