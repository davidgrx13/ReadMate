package com.example.readmatetasks.data.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DailyGoalRepository {

    suspend fun loadDailyGoal(context: Context, userId: String): Int = withContext(Dispatchers.IO) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        prefs.getInt("${userId}_daily_goal", 0)
    }

    suspend fun saveDailyGoal(context: Context, userId: String, minutes: Int) = withContext(Dispatchers.IO) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        prefs.edit().putInt("${userId}_daily_goal", minutes).apply()
    }
}