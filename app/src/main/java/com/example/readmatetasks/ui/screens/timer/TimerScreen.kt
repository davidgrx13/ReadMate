package com.example.readmatetasks.ui.screens.timer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.readmatetasks.R
import com.example.readmatetasks.data.repository.BookRepository
import com.example.readmatetasks.ui.components.BottomNavBar
import com.example.readmatetasks.ui.components.WishlistBookCard
import com.example.readmatetasks.ui.navigation.Routes
import com.example.readmatetasks.ui.session.ProgressViewModel
import com.example.readmatetasks.ui.theme.AccentColor
import com.example.readmatetasks.ui.theme.BackgroundColor
import com.example.readmatetasks.ui.theme.SecondAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    navController: NavController,
    timerViewModel: TimerViewModel,
    progressViewModel: ProgressViewModel
) {
    val currentRoute = Routes.Timer.route

    val remainingMinutes by timerViewModel.remainingMinutes.collectAsState()
    val remainingSeconds by timerViewModel.remainingSeconds.collectAsState()
    val minutesReadToday by timerViewModel.minutesReadToday.collectAsState()
    val isTimerRunning by timerViewModel.isTimerRunning.collectAsState()
    val dailyGoal by timerViewModel.dailyGoal.collectAsState()

    val pendingBooks = progressViewModel.wishlist
    val progressMap by progressViewModel.progressMap.collectAsState()

    // Lottie animation setup del timer
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.timer3))
    val lottieAnimatable = rememberLottieAnimatable()

    var showWinnerAnimation by remember { mutableStateOf(false) }
    val winnerAnimatable = rememberLottieAnimatable()
    val winnerComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.winner))

    LaunchedEffect(isTimerRunning, composition) {
        if (isTimerRunning) {
            lottieAnimatable.animate(
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
        }
    }

    LaunchedEffect(Unit) {
        timerViewModel.refreshDailyGoal()
    }

    val totalSecondsGoal = dailyGoal * 60
    val secondsRead = (minutesReadToday * 60) + (60 - remainingSeconds)
    val progress = if (totalSecondsGoal > 0) secondsRead.toFloat() / totalSecondsGoal else 0f

    Scaffold(
        topBar = {
            Spacer(modifier = Modifier.height(0.dp))
        },
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                navController = navController,
                modifier = Modifier
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            // Progress Bar
            Canvas(
                modifier = Modifier
                    .size(340.dp)
                    .align(Alignment.Center)
                    .offset(y = (-165).dp)
                    .zIndex(0f)
            ) {
                val strokeWidth = 45f
                val startAngle = 180f
                val sweepAngle = 180f

                drawArc(
                    color = SecondAccent,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(width = 52f, cap = StrokeCap.Round)
                )
                drawArc(
                    color = AccentColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle * progress,
                    useCenter = false,
                    style = Stroke(width = 52f, cap = StrokeCap.Round)
                )
            }

            // Textos
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-225).dp)
                    .zIndex(1f)
            ) {
                Text(
                    text = "Tu progreso",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = String.format("%02d:%02d", remainingMinutes, remainingSeconds),
                    fontSize = 64.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "de tu meta de $dailyGoal minutos",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }

            // Animación
            LottieAnimation(
                composition = composition,
                progress = { lottieAnimatable.progress },
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.Center)
                    .offset(y = (-10).dp)
                    .zIndex(2f)
            )

            // Botón Play/Pause
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (180).dp)
                    .zIndex(3f)
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.Transparent,
                    tonalElevation = 2.dp,
                    shadowElevation = 8.dp,
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(AccentColor)
                    ) {
                        IconButton(
                            onClick = {
                                if (isTimerRunning) {
                                    timerViewModel.pauseTimer()
                                } else {
                                    timerViewModel.startTimer()
                                }
                            },
                            modifier = Modifier.size(75.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = if (isTimerRunning) R.drawable.pause_buttom else R.drawable.play_buttom),
                                contentDescription = if (isTimerRunning) "Pause" else "Start",
                                tint = SecondAccent,
                                modifier = Modifier
                                    .size(35.dp)
                                    .then(
                                        if (!isTimerRunning) Modifier.offset(x = 2.dp) else Modifier
                                    )
                            )
                        }
                    }
                }

            }

            // CARRUSEL de Wishlist
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp) // Que no choque con navbar
                    .zIndex(4f),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                // Mostrar solo los libros no terminados
                items(pendingBooks, key = { it }) { bookId ->
                    val book = BookRepository.getBookById(bookId)
                    if (book != null) {
                        val currentProgress = progressMap[bookId] ?: 0
                        if (currentProgress in 0 until book.totalPages) {
                            WishlistBookCard(
                                book = book,
                                currentPage = currentProgress,
                                onUpdateProgress = { newPage ->
                                    progressViewModel.updateProgress(bookId, newPage)
                                    if (newPage >= book.totalPages) {
                                        showWinnerAnimation = true
                                    }
                                },
                                onRemove = {
                                    progressViewModel.removeFromWishlist(bookId)
                                },
                                onClickCard = {
                                    navController.navigate("book_detail/${book.id}") // Asegúrate que tengas esta ruta
                                }
                            )
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = showWinnerAnimation,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundColor.copy(alpha = 0.85f)) // Fondo semitransparente
                    .clickable(enabled = false) {}, // Bloquea interacción
                contentAlignment = Alignment.Center
            ) {
                // Lottie Animation
                LaunchedEffect(showWinnerAnimation) {
                    if (showWinnerAnimation && winnerComposition != null) {
                        winnerAnimatable.animate(
                            composition = winnerComposition,
                            iterations = 1,
                            speed = 0.8f
                        )
                        // Al terminar, ocultamos
                        showWinnerAnimation = false
                    }
                }

                LottieAnimation(
                    composition = winnerComposition,
                    progress = { winnerAnimatable.progress },
                    modifier = Modifier.size(250.dp)
                )
            }
        }
    }
}