package com.example.myapplicationmeuprimeiroapp.ui.screens

import androidx.compose.foundation.background // Importação essencial
import androidx.compose.ui.unit.sp   // <-- adicione esta linha
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplicationmeuprimeiroapp.data.local.ProjectEntity
import com.example.myapplicationmeuprimeiroapp.viewmodel.ProjectViewModel
import com.example.myapplicationmeuprimeiroapp.viewmodel.ProjectUiState

@Composable
fun ProjectDetailScreen(projectId: Int) {
    val viewModel: ProjectViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    var project by remember { mutableStateOf<ProjectEntity?>(null) }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is ProjectUiState.Success -> {
                project = state.projects.find { it.id == projectId }
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAEAEA))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Detalhes do Projeto",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6A11CB)
                )
                Spacer(modifier = Modifier.height(24.dp))

                if (project != null) {
                    Text(
                        text = project!!.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = project!!.description ?: "Sem descrição disponível",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Linguagem: ${project!!.language ?: "Não especificada"}",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                } else {
                    Text(
                        text = "Projeto não encontrado.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Red
                    )
                }
            }
        }
    }
}