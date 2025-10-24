package com.example.myapplicationmeuprimeiroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationmeuprimeiroapp.data.ProjectRepository
import com.example.myapplicationmeuprimeiroapp.data.local.ProjectEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class ProjectViewModel(private val repository: ProjectRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ProjectUiState>(ProjectUiState.Loading)
    val uiState: StateFlow<ProjectUiState> = _uiState.asStateFlow()

    init {
        // Carrega os projetos do GitHub assim que a ViewModel é criada
        refreshProjects("Willian-Fernandes1") // Substitua pelo seu username
    }

    fun refreshProjects(username: String) {
        viewModelScope.launch {
            _uiState.value = ProjectUiState.Loading

            try {
                // 1️⃣ Busca os dados da API do GitHub e atualiza o banco local (Room)
                repository.refreshProjects(username)

                // 2️⃣ Aguarda brevemente para exibir o indicador de carregamento
                delay(10000)

                // 3️⃣ Coleta os dados locais e atualiza a UI
                repository.getProjects().collect { projects ->
                    if (projects.isNotEmpty()) {
                        _uiState.value = ProjectUiState.Success(projects)
                    } else {
                        _uiState.value = ProjectUiState.Error("Nenhum projeto encontrado.")
                    }
                }

            } catch (e: Exception) {
                // 4️⃣ Caso ocorra erro, exibe mensagem amigável
                _uiState.value = ProjectUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}

// ----------------------
// Estados possíveis da tela
// ----------------------
sealed class ProjectUiState {
    object Loading : ProjectUiState()
    data class Success(val projects: List<ProjectEntity>) : ProjectUiState()
    data class Error(val message: String) : ProjectUiState()
}
