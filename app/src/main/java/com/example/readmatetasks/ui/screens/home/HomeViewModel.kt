package com.example.readmatetasks.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readmatetasks.data.model.Book
import com.example.readmatetasks.data.model.ReadingDay
import com.example.readmatetasks.data.repository.BookRepository
import com.example.readmatetasks.data.repository.DailyGoalRepository
import com.example.readmatetasks.data.repository.ReadingTimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(
    private val dailyGoalRepository: DailyGoalRepository,
    private val readingTimeRepository: ReadingTimeRepository,
    private val context: Context,
    private val userId: String
) : ViewModel() {

    private val _dailyGoal = MutableStateFlow(0)
    val dailyGoal = _dailyGoal.asStateFlow()

    private val _readingHistory = MutableStateFlow<List<ReadingDay>>(emptyList())
    val readingHistory = _readingHistory.asStateFlow()

    private val _recommendedBook = MutableStateFlow<Book?>(null)
    val recommendedBook = _recommendedBook.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            val goal = dailyGoalRepository.loadDailyGoal(context, userId)
            _dailyGoal.value = goal

            val fullList = readingTimeRepository.loadReadingTimes(context, userId)
            val filledList = fillMissingDays(fullList)
            _readingHistory.value = filledList

            // Cargar libro recomendado aleatorio
            val allBooks = BookRepository.getAllBooks()
            if (allBooks.isNotEmpty()) {
                _recommendedBook.value = allBooks.random()
            }
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

    fun refreshDailyGoal() {
        viewModelScope.launch {
            val goal = dailyGoalRepository.loadDailyGoal(context, userId)
            _dailyGoal.value = goal
        }
    }
}