package com.example.readmatetasks.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readmatetasks.data.repository.DailyGoalRepository
import com.example.readmatetasks.data.repository.ReadingTimeRepository

/**
 * Factory para crear instancias de [HomeViewModel].
 */
class HomeViewModelFactory(
    private val dailyGoalRepository: DailyGoalRepository,
    private val readingTimeRepository: ReadingTimeRepository,
    private val context: Context,
    private val userId: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                dailyGoalRepository,
                readingTimeRepository,
                context,
                userId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}