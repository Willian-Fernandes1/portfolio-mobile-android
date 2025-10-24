package com.example.myapplicationmeuprimeiroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.myapplicationmeuprimeiroapp.data.ProjectRepository
import com.example.myapplicationmeuprimeiroapp.data.local.AppDatabase
import com.example.myapplicationmeuprimeiroapp.data.local.ProjectDao
import com.example.myapplicationmeuprimeiroapp.data.remote.GitHubApiService
import com.example.myapplicationmeuprimeiroapp.data.remote.RetrofitInstance
import com.example.myapplicationmeuprimeiroapp.ui.screens.BusinessCard
import com.example.myapplicationmeuprimeiroapp.ui.screens.ProjectDetailScreen
import com.example.myapplicationmeuprimeiroapp.ui.screens.ProjectListScreen
import com.example.myapplicationmeuprimeiroapp.viewmodel.ProjectViewModel

class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase
    private lateinit var projectDao: ProjectDao
    private lateinit var apiService: GitHubApiService
    private lateinit var repository: ProjectRepository
    private lateinit var viewModel: ProjectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar o banco de dados
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()
        projectDao = database.projectDao()

        // Inicializar a API
        apiService = RetrofitInstance.api

        // Criar o Repository
        repository = ProjectRepository(projectDao, apiService)

        // Criar o ViewModel
        viewModel = ProjectViewModel(repository)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFEAEAEA)
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "profile"
                    ) {
                        composable("profile") {
                            BusinessCard(onNavigateToProjects = {
                                navController.navigate("projects")
                            })
                        }
                        composable("projects") {
                            ProjectListScreen(viewModel = viewModel, onProjectClick = { projectId ->
                                navController.navigate("project_detail/$projectId")
                            })
                        }
                        composable(
                            route = "project_detail/{projectId}",
                            arguments = listOf(navArgument("projectId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val projectId = backStackEntry.arguments?.getInt("projectId") ?: 0
                            ProjectDetailScreen(projectId = projectId)
                        }
                    }
                }
            }
        }
    }
}