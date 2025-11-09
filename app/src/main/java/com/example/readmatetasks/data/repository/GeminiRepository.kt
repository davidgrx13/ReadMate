package com.example.readmatetasks.data.repository

import com.google.firebase.Firebase
import com.google.firebase.vertexai.vertexAI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GeminiRepository {

    private val vertexAI = Firebase.vertexAI
    private val model = vertexAI.generativeModel("gemini-1.5-flash")

    fun getCuriositiesStream(bookTitle: String, bookAuthor: String): Flow<String> {
        val prompt = "Devuelve directamente, sin introducciones, una lista con 5 curiosidades breves (doble salto de línea entre ellas y numeradas) y originales sobre el libro “$bookTitle”,(autor “$bookAuthor”). Usa un estilo fresco, dinámico y atractivo, adecuado para mostrarse en una interfaz móvil juvenil. El texto debe ser exclusivamente plano, sin usar markdown ni símbolos especiales de formato (como asteriscos, guiones o negritas). Puedes incluir algún emoji ocasionalmente, pero evita cualquier otro formato. No incluyas saludos, despedidas ni introducciones genéricas, solo la lista."

        return model.generateContentStream(prompt).map { it.text.orEmpty() }
    }
}