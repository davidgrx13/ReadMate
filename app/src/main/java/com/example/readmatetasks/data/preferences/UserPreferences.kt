package com.example.readmatetasks.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Crea el DataStore
private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {

    companion object {
        val DAILY_GOAL = intPreferencesKey("daily_goal_minutes")
        val USER_ID = stringPreferencesKey("user_id") // Para diferenciar usuarios
    }

    // Obtener el daily goal
    val dailyGoalFlow: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[DAILY_GOAL] ?: 0 // Default 0 minutos
        }

    // Guardar el daily goal
    suspend fun setDailyGoal(minutes: Int) {
        context.dataStore.edit { preferences ->
            preferences[DAILY_GOAL] = minutes
        }
    }
}