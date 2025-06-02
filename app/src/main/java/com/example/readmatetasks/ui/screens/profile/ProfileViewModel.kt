package com.example.readmatetasks.ui.screens.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmatetasks.data.model.ReadingDay
import com.example.readmatetasks.data.repository.DailyGoalRepository
import com.example.readmatetasks.data.repository.ReadingTimeRepository
import com.example.readmatetasks.ui.session.AuthSessionViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProfileViewModel(
    private val authSessionViewModel: AuthSessionViewModel,
    private val dailyGoalRepository: DailyGoalRepository,
    private val readingTimeRepository: ReadingTimeRepository
) : ViewModel() {

    private val _dailyGoal = MutableStateFlow(0)
    val dailyGoal = _dailyGoal.asStateFlow()

    private val _averageReadingMinutes = MutableStateFlow(0)
    val averageReadingMinutes: StateFlow<Int> = _averageReadingMinutes

    fun logout() {
        authSessionViewModel.logout()
    }

    fun loadDailyGoal(context: Context) {
        viewModelScope.launch {
            val userId = authSessionViewModel.getCurrentUserId() ?: "default_user"
            val goal = dailyGoalRepository.loadDailyGoal(context, userId)
            _dailyGoal.value = goal
        }
    }

    fun saveDailyGoal(minutes: Int, context: Context) {
        viewModelScope.launch {
            val userId = authSessionViewModel.getCurrentUserId() ?: "default_user"
            dailyGoalRepository.saveDailyGoal(context, userId, minutes)
            _dailyGoal.value = minutes
        }
    }

    fun loadAverageReadingMinutes(context: Context) {
        viewModelScope.launch {
            val userId = authSessionViewModel.getCurrentUserId() ?: return@launch
            val fullList = readingTimeRepository.loadReadingTimes(context, userId)
            val filledList = fillMissingDays(fullList)

            val totalMinutes = filledList.sumOf { it.minutes }
            val average = if (filledList.isNotEmpty()) totalMinutes / filledList.size else 0
            _averageReadingMinutes.value = average
        }
    }

    private fun fillMissingDays(readList: List<ReadingDay>): List<ReadingDay> {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = Calendar.getInstance()
        val filledList = mutableListOf<ReadingDay>()

        for (i in 6 downTo 0) {
            val cal = Calendar.getInstance()
            cal.time = today.time
            cal.add(Calendar.DAY_OF_YEAR, -i)
            val dateStr = sdf.format(cal.time)

            val existing = readList.find { it.date == dateStr }
            if (existing != null) {
                filledList.add(existing)
            } else {
                filledList.add(ReadingDay(dateStr, 0))
            }
        }
        return filledList
    }
}