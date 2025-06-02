package com.example.readmatetasks.ui.screens.timer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readmatetasks.data.repository.DailyGoalRepository
import com.example.readmatetasks.data.repository.ReadingTimeRepository

/**
 * Factory para crear instancias de [TimerViewModel] con sus dependencias inyectadas.
 *
 * @param dailyGoalRepository Repositorio que gestiona el daily goal.
 * @param readingTimeRepository Repositorio que gestiona el tiempo de lectura.
 * @param context Contexto para acceder a SharedPreferences.
 * @param userId ID del usuario actual.
 */
class TimerViewModelFactory(
    private val dailyGoalRepository: DailyGoalRepository,
    private val readingTimeRepository: ReadingTimeRepository,
    private val context: Context,
    private val userId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            return TimerViewModel(
                dailyGoalRepository,
                readingTimeRepository,
                context,
                userId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}