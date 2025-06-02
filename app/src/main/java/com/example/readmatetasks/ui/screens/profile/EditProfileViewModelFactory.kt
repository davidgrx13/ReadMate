package com.example.readmatetasks.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.readmatetasks.ui.session.AuthSessionViewModel

/**
 * Factory para crear instancias de [EditProfileViewModel] con un [AuthSessionViewModel] inyectado.
 *
 * @param authSessionViewModel ViewModel que gestiona la sesión del usuario.
 */
class EditProfileViewModelFactory(private val authSessionViewModel: AuthSessionViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(authSessionViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
