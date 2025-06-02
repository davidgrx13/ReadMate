package com.example.readmatetasks.ui.screens.profile

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmatetasks.ui.session.AuthSessionViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditProfileViewModel(private val authSessionViewModel: AuthSessionViewModel) : ViewModel() {

    val username: StateFlow<String?> = authSessionViewModel.username
    val userEmail: StateFlow<String?> = authSessionViewModel.userEmail
    val profileImage: StateFlow<Bitmap?> = authSessionViewModel.profileImage

    fun updateUsername(newUsername: String) {
        viewModelScope.launch {
            authSessionViewModel.updateUsername(newUsername)
        }
    }

    fun updatePassword(newPassword: String) {
        viewModelScope.launch {
            authSessionViewModel.updatePassword(newPassword)
        }
    }

    fun setProfileImage(context: Context, bitmap: Bitmap) {
        viewModelScope.launch {
            authSessionViewModel.saveProfileImage(context, bitmap)
        }
    }

    fun removeProfileImage(context: Context) {
        viewModelScope.launch {
            authSessionViewModel.deleteProfileImage(context)
        }
    }

    fun fetchProfileImage(context: Context) {
        viewModelScope.launch {
            authSessionViewModel.fetchProfileImage(context)
        }
    }
}