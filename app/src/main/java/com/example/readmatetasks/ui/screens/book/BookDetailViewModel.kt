package com.example.readmatetasks.ui.screens.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmatetasks.data.repository.GeminiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// El constructor ahora recibe el repositorio
class BookDetailViewModel(private val repository: GeminiRepository) : ViewModel() {

    private val _curiosities = MutableStateFlow("")
    val curiosities: StateFlow<String> = _curiosities

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchCuriosities(bookTitle: String, bookAuthor: String) {
        _isLoading.value = true
        _curiosities.value = ""

        viewModelScope.launch {
            try {
                // Llama al método del repositorio para obtener el flujo
                repository.getCuriositiesStream(bookTitle, bookAuthor)
                    .collect {
                        _curiosities.value += it
                    }
            } catch (e: Exception) {
                _curiosities.value = "No ha habido respuesta de Gemini."
            } finally {
                _isLoading.value = false
            }
        }
    }
}