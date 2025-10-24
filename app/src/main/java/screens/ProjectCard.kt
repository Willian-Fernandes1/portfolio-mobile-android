package com.example.myapplicationmeuprimeiroapp.ui.screens

import androidx.compose.ui.unit.sp   // <-- adicione esta linha
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplicationmeuprimeiroapp.Project

@Composable
fun ProjectCard(project: Project, onProjectClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onProjectClick(project.id) },
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = project.name,
                fontSize = 18.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
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