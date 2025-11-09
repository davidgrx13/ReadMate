package com.example.readmatetasks.data.repository

import android.content.Context
import com.example.readmatetasks.data.model.ReadingDay
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReadingTimeRepository {

    private val gson = Gson()
    private val maxDays = 7

    suspend fun loadReadingTimes(context: Context, userId: String): List<ReadingDay> = withContext(Dispatchers.IO) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val json = prefs.getString("${userId}_reading_times", null)
        if (json != null) {
            val type = object : TypeToken<List<ReadingDay>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    suspend fun saveReadingTime(context: Context, userId: String, newDay: ReadingDay) = withContext(Dispatchers.IO) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val currentList = loadReadingTimes(context, userId).toMutableList()

        // Si ya hay un día con esa fecha, actualiza
        val existingIndex = currentList.indexOfFirst { it.date == newDay.date }
        if (existingIndex != -1) {
            currentList[existingIndex] = newDay
        } else {
            // Si es nuevo, añadimos
            if (currentList.size >= maxDays) {
                currentList.removeAt(0) // Quita el más antiguo
            }
            currentList.add(newDay)
        }

        // Guardamos actualizado
        val json = gson.toJson(currentList)
        prefs.edit().putString("${userId}_reading_times", json).apply()
    }

}