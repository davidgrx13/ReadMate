package com.example.readmatetasks.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProgressRepository(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("reading_progress", Context.MODE_PRIVATE)

    private val gson = Gson()

    // Structure: Map<userId, Map<bookId, currentPage>>
    private var progressData: MutableMap<String, MutableMap<String, Int>> = mutableMapOf()

    // Wishlist: Map<userId, Set<bookId>>
    private var wishlistData: MutableMap<String, MutableSet<String>> = mutableMapOf()

    init {
        loadProgressData()
        loadWishlistData()
    }

    // ------------ PROGRESS ------------
    private fun loadProgressData() {
        val json = sharedPreferences.getString("progress_data", null)
        if (json != null) {
            val type = object : TypeToken<MutableMap<String, MutableMap<String, Int>>>() {}.type
            progressData = gson.fromJson(json, type) ?: mutableMapOf()
        }
    }

    private fun saveProgressData() {
        val json = gson.toJson(progressData)
        sharedPreferences.edit().putString("progress_data", json).apply()
    }

    fun getProgress(userId: String, bookId: String): Int {
        return progressData[userId]?.get(bookId) ?: 0
    }

    fun updateProgress(userId: String, bookId: String, currentPage: Int) {
        val userProgress = progressData.getOrPut(userId) { mutableMapOf() }
        userProgress[bookId] = currentPage
        saveProgressData()
    }

    fun getAllProgressForUser(userId: String): Map<String, Int> {
        return progressData[userId] ?: emptyMap()
    }

    // ------------ WISHLIST ------------

    private fun loadWishlistData() {
        val json = sharedPreferences.getString("wishlist_data", null)
        if (json != null) {
            val type = object : TypeToken<MutableMap<String, MutableSet<String>>>() {}.type
            wishlistData = gson.fromJson(json, type) ?: mutableMapOf()
        }
    }

    private fun saveWishlistData() {
        val json = gson.toJson(wishlistData)
        sharedPreferences.edit().putString("wishlist_data", json).apply()
    }

    fun getWishlistForUser(userId: String): Set<String> {
        return wishlistData[userId] ?: emptySet()
    }

    fun addBookToWishlist(userId: String, bookId: String) {
        val userWishlist = wishlistData.getOrPut(userId) { mutableSetOf() }
        userWishlist.add(bookId)
        saveWishlistData()
    }

    fun removeBookFromWishlist(userId: String, bookId: String) {
        val userWishlist = wishlistData[userId]
        userWishlist?.remove(bookId)
        saveWishlistData()
    }
}