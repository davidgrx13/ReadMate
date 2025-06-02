package com.example.readmatetasks.ui.session

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmatetasks.data.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProgressViewModel(
    application: Application,
    private var userId: String
) : AndroidViewModel(application) {

    private val progressRepository = ProgressRepository(application.applicationContext)

    private val _progressMap = MutableStateFlow<Map<String, Int>>(emptyMap())
    val progressMap: StateFlow<Map<String, Int>> = _progressMap

    private val _wishlist = mutableStateListOf<String>()
    val wishlist: SnapshotStateList<String> = _wishlist

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val progress = progressRepository.getAllProgressForUser(userId)
            val wishlistSet = progressRepository.getWishlistForUser(userId)

            _progressMap.value = progress
            _wishlist.clear()
            _wishlist.addAll(wishlistSet)
        }
    }

    fun refreshForNewUser(newUserId: String) {
        userId = newUserId
        loadUserData()
    }

    fun updateProgress(bookId: String, currentPage: Int) {
        viewModelScope.launch {
            progressRepository.updateProgress(userId, bookId, currentPage)
            val updatedMap = _progressMap.value.toMutableMap()
            updatedMap[bookId] = currentPage
            _progressMap.value = updatedMap
        }
    }

    fun addToWishlist(bookId: String) {
        viewModelScope.launch {
            progressRepository.addBookToWishlist(userId, bookId)
            if (!wishlist.contains(bookId)) {
                _wishlist.add(bookId)
            }
        }
    }

    fun removeFromWishlist(bookId: String) {
        viewModelScope.launch {
            progressRepository.removeBookFromWishlist(userId, bookId)
            _wishlist.remove(bookId)
        }
    }
}