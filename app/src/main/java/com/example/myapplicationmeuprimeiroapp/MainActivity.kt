package com.example.myapplicationmeuprimeiroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                            ProjectListScreen(projects = MockData.getProjects(), onProjectClick = { projectId ->
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

// Tela de Perfil (BusinessCard)
@Composable
fun BusinessCard(onNavigateToProjects: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(24.dp))
                .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(24.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.perfil),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(3.dp, Color.White, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "WILLIAN FERNANDES PAIVA",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Graduado em Sistemas para Internet",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(28.dp))
            ContactRow(icon = Icons.Default.Call, text = "(84) 98888-1234")
            Spacer(modifier = Modifier.height(16.dp))
            ContactRow(icon = Icons.Default.Email, text = "willianfernandes@gmail.com")
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ContactItemWithImage(
                    iconRes = R.drawable.linkedin,
                    text = "LinkedIn",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                ContactItemWithImage(
                    iconRes = R.drawable.github,
                    text = "GitHub",
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onNavigateToProjects,
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A11CB),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Ver Meus Projetos", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ContactItemWithImage(iconRes: Int, text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            maxLines = 1
        )
    }
}

@Composable
fun ContactRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF1A56DB),
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

// Tela de Lista de Projetos
@Composable
fun ProjectListScreen(projects: List<Project>, onProjectClick: (Int) -> Unit) {
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(projects) { project ->
                ProjectCard(project = project, onProjectClick = onProjectClick)
            }
        }
    }
}

@Composable
fun ProjectCard(project: Project, onProjectClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProjectClick(project.id) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = project.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6A11CB)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = project.description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

// Tela de Detalhes do Projeto (modificada para descrições detalhadas)
@Composable
fun ProjectDetailScreen(projectId: Int) {
    // Buscar o projeto correspondente pelo ID
    val project = MockData.getProjects().find { it.id == projectId }

    // Textos detalhados por projeto
    val projectDetails = mapOf(
        1 to "Aplicativo de Notas: Um app completo para organizar suas notas diárias, criar listas de tarefas e lembretes. Ideal para quem quer estudar programação Android, manipulação de dados e arquitetura de apps modernos.",
        2 to "E-commerce de Roupas: Plataforma online para venda de roupas com carrinho de compras, filtragem de produtos e integração com métodos de pagamento. Excelente para praticar back-end, front-end e gerenciamento de banco de dados.",
        3 to "Sistema de Gestão: Sistema para gerenciar pequenas empresas, com controle de estoque, funcionários e vendas. Ótimo para aprender programação orientada a objetos, banco de dados relacional e lógica de negócios.",
        4 to "Pizzalab: Aplicativo que permite aos usuários realizar pedidos de pizza de forma prática e interativa. Permite explorar o cardápio, personalizar sabores, acompanhar pedidos e melhorar a experiência do cliente.",
        5 to "App de Receitas: Aplicativo para compartilhar receitas culinárias, com categorias, fotos e avaliações. Excelente para explorar desenvolvimento mobile, manipulação de dados e integração com APIs externas.",
    )

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
            shape = RoundedCornerShape(12.dp),
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
                        text = project.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = projectDetails[project.id] ?: project.description,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
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

// Data class para modelar os projetos
data class Project(
    val id: Int,
    val name: String,
    val description: String
)

// Objeto para fornecer dados mockados
object MockData {
    fun getProjects(): List<Project> {
        return listOf(
            Project(1, "Aplicativo de Notas", "Um aplicativo para organizar suas notas diárias"),
            Project(2, "E-commerce de Roupas", "Plataforma para venda de roupas online"),
            Project(3, "Sistema de Gestão", "Sistema para gerenciar pequenas empresas"),
            Project(4, "App Pizzalab", "App de pizzaria para pedidos, personalização de pizzas e acompanhamento do pedido"),
            Project(5, "App de Receitas", "Aplicativo para compartilhar receitas culinárias")
        )
    }
}
