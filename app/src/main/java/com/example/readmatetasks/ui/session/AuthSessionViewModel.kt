package com.example.readmatetasks.ui.session

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmatetasks.data.repository.AuthRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel que maneja la sesión del usuario, obtención de sus datos y gestión de fotos de perfil.
 */
class AuthSessionViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _userEmail = MutableStateFlow<String?>(null)
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username.asStateFlow()

    private val _profileImage = MutableStateFlow<Bitmap?>(null)
    val profileImage: StateFlow<Bitmap?> = _profileImage.asStateFlow()

    // Flag para saber si ya comprobaste el estado de autenticación
    private val _isAuthCheckDone = MutableStateFlow(false)
    val isAuthCheckDone: StateFlow<Boolean> = _isAuthCheckDone.asStateFlow()

    init {
        fetchCurrentUser() // Se lanza automáticamente al crear el ViewModel
    }

    /**
     * Comprueba si hay un usuario autenticado y obtiene sus datos.
     */
     fun fetchCurrentUser() {
        val userId = authRepository.getCurrentUserId()
        if (userId != null) {
            viewModelScope.launch {
                val result = authRepository.getUserDetails(userId)
                if (result.isSuccess) {
                    val userDetails = result.getOrNull()
                    _username.value = userDetails?.get("username") as? String
                    _userEmail.value = userDetails?.get("email") as? String
                } else {
                    _username.value = null
                    _userEmail.value = null
                }
                _isAuthCheckDone.value = true
            }
        } else {
            // No hay usuario, marcamos como comprobado
            _isAuthCheckDone.value = true
        }
    }

    /**
     * Obtiene el ID del usuario actualmente autenticado.
     */
    fun getCurrentUserId(): String? {
        return authRepository.getCurrentUserId()
    }

    /**
     * Cierra la sesión del usuario y limpia los datos almacenados en la sesión actual.
     */
    fun logout() {
        authRepository.logoutUser()
        _username.value = null
        _userEmail.value = null
    }

    /**
     * Actualiza el nombre de usuario en Firestore.
     */
    fun updateUsername(newUsername: String) {
        val userId = authRepository.getCurrentUserId() ?: return
        viewModelScope.launch {
            authRepository.updateUsername(userId, newUsername)
            _username.value = newUsername
        }
    }

    /**
     * Cambia la contraseña del usuario autenticado.
     */
    fun updatePassword(newPassword: String) {
        viewModelScope.launch {
            authRepository.updatePassword(newPassword)
        }
    }

    /**
     * Obtiene la imagen de perfil desde la memoria interna.
     */
    fun fetchProfileImage(context: Context) {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId() ?: return@launch
            _profileImage.value = authRepository.getProfileImage(context, userId)
        }
    }

    /**
     * Guarda la imagen de perfil en la memoria interna.
     */
    fun saveProfileImage(context: Context, bitmap: Bitmap) {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId() ?: return@launch
            if (authRepository.saveProfileImage(context, userId, bitmap)) {
                _profileImage.value = bitmap
            }
        }
    }

    /**
     * Elimina la imagen de perfil almacenada localmente.
     */
    fun deleteProfileImage(context: Context) {
        viewModelScope.launch {
            val userId = authRepository.getCurrentUserId() ?: return@launch
            if (authRepository.deleteProfileImage(context, userId)) {
                _profileImage.value = null
            }
        }
    }
}