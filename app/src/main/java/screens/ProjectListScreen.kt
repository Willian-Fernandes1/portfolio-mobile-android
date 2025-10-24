package com.example.myapplicationmeuprimeiroapp.ui.screens

import androidx.compose.foundation.background // Importação essencial
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplicationmeuprimeiroapp.Project
import com.example.myapplicationmeuprimeiroapp.viewmodel.ProjectViewModel
import com.example.myapplicationmeuprimeiroapp.viewmodel.ProjectUiState

@Composable
fun ProjectListScreen(viewModel: ProjectViewModel, onProjectClick: (Int) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAEAEA))
            .padding(16.dp)
    ) {
        Text(
            text = "Meus Projetos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (val state = uiState) {
            is ProjectUiState.Loading -> {
                // Indicador de carregamento
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF6A11CB),
                            strokeWidth = 4.dp,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Carregando projetos...",
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                }
            }
            is ProjectUiState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.projects) { project ->
                        ProjectCard(
                            project = Project(
                                id = project.id,
                                name = project.name,
                                description = project.description ?: "Sem descrição"
                            ),
                            onProjectClick = onProjectClick
                        )
                    }
                }
            }
            is ProjectUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Erro ao carregar projetos",
                            color = Color.Red,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.message,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        androidx.compose.material3.Button(
                            onClick = { viewModel.refreshProjects("Willian-Fernandes1") }, // Substitua pelo seu username
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6A11CB)
                            )
                        ) {
                            Text(text = "Tentar novamente")
                        }
                    }
                }
            }
        }
    }
}