package com.example.readmatetasks.ui.screens.timer

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmatetasks.data.repository.DailyGoalRepository
import com.example.readmatetasks.data.model.ReadingDay
import com.example.readmatetasks.data.repository.ReadingTimeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TimerViewModel(
    private val dailyGoalRepository: DailyGoalRepository,
    private val readingTimeRepository: ReadingTimeRepository,
    private val context: Context,
    private val userId: String
) : ViewModel() {

    private val _dailyGoal = MutableStateFlow(0)
    val dailyGoal = _dailyGoal.asStateFlow()

    private val _minutesReadToday = MutableStateFlow(0)
    val minutesReadToday = _minutesReadToday.asStateFlow()

    private val _remainingMinutes = MutableStateFlow(0)
    val remainingMinutes = _remainingMinutes.asStateFlow()

    private val _remainingSeconds = MutableStateFlow(0)
    val remainingSeconds = _remainingSeconds.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning = _isTimerRunning.asStateFlow()

    private var timerJob: kotlinx.coroutines.Job? = null

    private var hasLoadedData = false

    init {
        loadDataOnce()
    }

    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }

    private fun loadDataOnce() {
        if (hasLoadedData) return
        hasLoadedData = true

        viewModelScope.launch {
            val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

            val goal = dailyGoalRepository.loadDailyGoal(context, userId)
            _dailyGoal.value = goal

            val readingList = readingTimeRepository.loadReadingTimes(context, userId)
            val today = getTodayDate()
            val todayReading = readingList.find { it.date == today }?.minutes ?: 0
            _minutesReadToday.value = todayReading

            val lastDate = prefs.getString("${userId}_last_date", null)

            if (lastDate == today) {
                val savedMinutes = prefs.getInt("${userId}_remaining_minutes", (goal - todayReading).coerceAtLeast(0))
                val savedSeconds = prefs.getInt("${userId}_remaining_seconds", 0)

                _remainingMinutes.value = savedMinutes
                _remainingSeconds.value = savedSeconds
            } else {
                _remainingMinutes.value = (goal - todayReading).coerceAtLeast(0)
                _remainingSeconds.value = 0
            }
        }
    }

    fun refreshDailyGoal() {
        viewModelScope.launch {
            val goal = dailyGoalRepository.loadDailyGoal(context, userId)
            _dailyGoal.value = goal

            val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val readingList = readingTimeRepository.loadReadingTimes(context, userId)
            val today = getTodayDate()
            val todayReading = readingList.find { it.date == today }?.minutes ?: 0
            _minutesReadToday.value = todayReading

            val remaining = (goal - todayReading).coerceAtLeast(0)
            _remainingMinutes.value = remaining

            if (remaining == 0) {
                _remainingSeconds.value = 0
                pauseTimer() // solo pausamos si no queda nada
            }

            prefs.edit()
                .putInt("${userId}_remaining_minutes", remaining)
                .putString("${userId}_last_date", today)
                .apply()
        }
    }

    fun startTimer() {
        if (_isTimerRunning.value || (_remainingMinutes.value <= 0 && _remainingSeconds.value <= 0)) return

        _isTimerRunning.value = true

        timerJob = viewModelScope.launch {
            while (_isTimerRunning.value && (_remainingMinutes.value > 0 || _remainingSeconds.value > 0)) {
                delay(1000)

                if (_remainingSeconds.value > 0) {
                    _remainingSeconds.value -= 1
                } else if (_remainingMinutes.value > 0) {
                    _remainingMinutes.value -= 1
                    _remainingSeconds.value = 59
                    _minutesReadToday.value += 1
                }
            }

            if (_remainingMinutes.value <= 0 && _remainingSeconds.value <= 0) {
                _isTimerRunning.value = false
                saveState()
            }
        }
    }

    fun pauseTimer() {
        if (!_isTimerRunning.value) return

        _isTimerRunning.value = false
        timerJob?.cancel()
        saveState()
    }

    private fun saveState() {
        viewModelScope.launch {
            val today = getTodayDate()
            readingTimeRepository.saveReadingTime(
                context,
                userId,
                ReadingDay(today, _minutesReadToday.value)
            )

            val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            prefs.edit()
                .putInt("${userId}_remaining_minutes", _remainingMinutes.value)
                .putInt("${userId}_remaining_seconds", _remainingSeconds.value)
                .putString("${userId}_last_date", today)
                .apply()
        }
    }
}