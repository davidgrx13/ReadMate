package com.example.readmatetasks.ui.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.readmatetasks.ui.components.BottomNavBar
import com.example.readmatetasks.ui.components.HomeBookCard
import com.example.readmatetasks.ui.navigation.Routes
import com.example.readmatetasks.ui.screens.timer.TimerViewModel
import com.example.readmatetasks.ui.session.AuthSessionViewModel
import com.example.readmatetasks.ui.session.ProgressViewModel
import com.example.readmatetasks.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    authSessionViewModel: AuthSessionViewModel,
    navController: NavController,
    timerViewModel: TimerViewModel,
    progressViewModel: ProgressViewModel
) {
    val currentRoute = navController.currentDestination?.route ?: Routes.Home.route

    val remainingMinutes by timerViewModel.remainingMinutes.collectAsState()
    val remainingSeconds by timerViewModel.remainingSeconds.collectAsState()
    val minutesReadToday by timerViewModel.minutesReadToday.collectAsState()
    val timerGoal by timerViewModel.dailyGoal.collectAsState()

    val readingHistory by homeViewModel.readingHistory.collectAsState()
    val homeGoal by homeViewModel.dailyGoal.collectAsState()

    val totalSecondsGoal = timerGoal * 60
    val secondsRead = (minutesReadToday * 60) + (60 - remainingSeconds)
    val progress = if (totalSecondsGoal > 0) secondsRead.toFloat() / totalSecondsGoal else 0f

    val recommendedBook by homeViewModel.recommendedBook.collectAsState()

    LaunchedEffect(Unit) {
        authSessionViewModel.fetchCurrentUser()
        timerViewModel.refreshDailyGoal()
        homeViewModel.loadData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lectura",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = MainTitleColor,
                            fontSize = 38.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp)
                            .graphicsLayer { alpha = 0.9f },
                        textAlign = androidx.compose.ui.text.style.TextAlign.Left
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundColor)
            )
        },
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                navController = navController,
                modifier = Modifier
                    .graphicsLayer { alpha = 0.9f }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {
            // Círculo decorativo de fondo
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(0f)
            ) {
                drawCircle(
                    color = SecondAccent,
                    radius = size.width * 0.75f,
                    center = Offset(
                        x = size.width + (size.width * 0.25f),
                        y = size.height / 2f
                    )
                )
            }
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(0f)
            ) {
                drawCircle(
                    color = SecondAccent,
                    radius = size.width * 0.55f,
                    center = Offset(
                        x = size.width * 0.1f, // Mitad de pantalla
                        y = size.height
                    )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding() - 8.dp,
                        bottom = 0.dp
                    )
                    .padding(vertical = 24.dp)
                    .zIndex(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Surface(
                    modifier = Modifier
                        .graphicsLayer { alpha = 0.85f }
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .shadow(
                            elevation = 8.dp,
                            shape = MaterialTheme.shapes.large,
                            ambientColor = AccentColor.copy(alpha = 0.5f),
                            spotColor = AccentColor.copy(alpha = 0.45f)
                        ),
                    shape = MaterialTheme.shapes.large,
                    tonalElevation = 5.dp,
                    shadowElevation = 20.dp,
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Meta de lectura",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp,
                                    color = Color.Black
                                ),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Left,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "Progreso diario:",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                ),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Left,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .zIndex(1f)
                                .offset(y = (20).dp)
                                .height(320.dp)
                        ) {
                            Canvas(
                                modifier = Modifier
                                    .size(320.dp)
                                    .zIndex(0f)
                            ) {
                                val startAngle = 180f
                                val sweepAngle = 180f

                                drawArc(
                                    color = SecondAccent,
                                    startAngle = startAngle,
                                    sweepAngle = sweepAngle,
                                    useCenter = false,
                                    style = Stroke(width = 45f, cap = StrokeCap.Round)
                                )
                                drawArc(
                                    color = AccentColor,
                                    startAngle = startAngle,
                                    sweepAngle = sweepAngle * progress,
                                    useCenter = false,
                                    style = Stroke(width = 45f, cap = StrokeCap.Round)
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .zIndex(1f)
                                    .offset(y = (-42).dp)
                            ) {
                                Text(
                                    text = "Lectura de hoy",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = String.format("%02d:%02d", remainingMinutes, remainingSeconds),
                                    fontSize = 48.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "de tu meta de $timerGoal minutos",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .zIndex(1f)
                                .offset(y = (-80).dp),
                        ) {
                            Text(
                                text = "Últimos siete días:",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 20.sp,
                                color = MainTitleColor
                            )
                        }

                        LazyRow(
                            modifier = Modifier
                                .zIndex(2f)
                                .fillMaxWidth()
                                .offset(y = (-30).dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val today = Calendar.getInstance()
                            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val historyMap = readingHistory.associateBy { it.date }

                            (6 downTo 0).forEach { i ->
                                val cal = Calendar.getInstance()
                                cal.add(Calendar.DAY_OF_YEAR, -i)
                                val dateStr = sdf.format(cal.time)
                                val dayNumber = cal.get(Calendar.DAY_OF_MONTH)

                                val dayReading = historyMap[dateStr]?.minutes ?: 0
                                val percentage = if (homeGoal > 0) {
                                    (dayReading.toFloat() / homeGoal).coerceAtMost(1f)
                                } else 0f

                                item {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .size(45.dp)
                                            .padding(4.dp)
                                            .offset(y = (-30).dp)
                                    ) {
                                        Canvas(
                                            modifier = Modifier.size(30.dp)
                                        ) {
                                            drawCircle(
                                                color = SecondAccent,
                                                style = Stroke(width = 18f)
                                            )
                                            drawArc(
                                                color = AccentColor,
                                                startAngle = -90f,
                                                sweepAngle = 360f * percentage,
                                                useCenter = false,
                                                style = Stroke(width = 18f, cap = StrokeCap.Round)
                                            )
                                        }
                                        Text(
                                            text = dayNumber.toString(),
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MainTitleColor
                                        )
                                    }
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate(Routes.Timer.route)
                                },
                                shape = MaterialTheme.shapes.medium,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AccentColor
                                ),
                                modifier = Modifier
                                    .zIndex(3f)
                                    .width(300.dp)
                                    .height(56.dp)
                                    .offset(y = (-20).dp)
                            ) {
                                Text(
                                    text = "Continuar leyendo",
                                    style = MaterialTheme.typography.titleMedium.copy(color = SecondAccent)
                                )
                            }
                        }
                    }
                }
                recommendedBook?.let { book ->
                    Spacer(modifier = Modifier.height(16.dp))
                    HomeBookCard(
                        book = book,
                        onClickCard = {
                            navController.navigate("book_detail/${book.id}")
                        }
                    )
                }
            }

        }
    }
}
