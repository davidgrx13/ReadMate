package com.example.readmatetasks.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readmatetasks.data.repository.DailyGoalRepository
import com.example.readmatetasks.data.repository.ReadingTimeRepository
import com.example.readmatetasks.ui.session.AuthSessionViewModel

/**
 * Factory para crear instancias de [ProfileViewModel] con un [AuthSessionViewModel] y [DailyGoalRepository] inyectados.
 *
 * @param authSessionViewModel ViewModel que gestiona la sesión del usuario.
 * @param dailyGoalRepository Repositorio que gestiona la meta diaria del usuario.
 */
class ProfileViewModelFactory(
    private val authSessionViewModel: AuthSessionViewModel,
    private val dailyGoalRepository: DailyGoalRepository,
    private val readingTimeRepository: ReadingTimeRepository
) : ViewModelProvider.Factory {

    /**
     * Crea una instancia de [ProfileViewModel] si la clase coincide.
     *
     * @param modelClass Clase del ViewModel a crear.
     * @return Instancia de [ProfileViewModel].
     * @throws IllegalArgumentException Si la clase no es compatible.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(authSessionViewModel, dailyGoalRepository, readingTimeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}