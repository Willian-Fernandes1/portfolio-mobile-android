package com.example.myapplicationmeuprimeiroapp.data

import com.example.myapplicationmeuprimeiroapp.data.local.ProjectDao
import com.example.myapplicationmeuprimeiroapp.data.local.ProjectEntity
import com.example.myapplicationmeuprimeiroapp.data.remote.GitHubApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProjectRepository(
    private val projectDao: ProjectDao,
    private val apiService: GitHubApiService
) {
    fun getProjects(): Flow<List<ProjectEntity>> {
        return projectDao.getAllProjects()
    }

    suspend fun refreshProjects(username: String) {
        try {
            val repos = apiService.getRepositories(username)
            val projects = repos.map { repo ->
                ProjectEntity(
                    id = repo.id,
                    name = repo.name,
                    description = repo.description,
                    html_url = repo.html_url,
                    language = repo.language
                )
            }
            projectDao.clearAll()
            projectDao.insertAll(projects)
        } catch (e: Exception) {
            throw e
        }
    }
}