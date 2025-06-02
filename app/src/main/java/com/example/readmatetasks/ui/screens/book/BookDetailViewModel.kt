package com.example.readmatetasks.ui.screens.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.vertexai.vertexAI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class BookDetailViewModel : ViewModel() {

    private val _curiosities = MutableStateFlow("")
    val curiosities: StateFlow<String> = _curiosities

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val vertexAI = Firebase.vertexAI
    private val model = vertexAI.generativeModel("gemini-1.5-flash")

    fun fetchCuriosities(bookTitle: String, bookAuthor: String) {
        _isLoading.value = true
        _curiosities.value = ""

        viewModelScope.launch {
            try {
                model.generateContentStream(
                    "Devuelve directamente, sin introducciones, una lista con 5 curiosidades breves (doble salto de línea entre ellas y numeradas) y originales sobre el libro “$bookTitle”,(autor “$bookAuthor”). Usa un estilo fresco, dinámico y atractivo, adecuado para mostrarse en una interfaz móvil juvenil. El texto debe ser exclusivamente plano, sin usar markdown ni símbolos especiales de formato (como asteriscos, guiones o negritas). Puedes incluir algún emoji ocasionalmente, pero evita cualquier otro formato. No incluyas saludos, despedidas ni introducciones genéricas, solo la lista."
                ).map { it.text.orEmpty() }.collect {
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