package com.example.readmatetasks.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.readmatetasks.ui.navigation.Routes
import com.example.readmatetasks.ui.session.AuthSessionViewModel
import kotlinx.coroutines.delay
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.example.readmatetasks.R
import com.example.readmatetasks.ui.theme.BackgroundColor

@Composable
fun SplashScreen(
    navController: NavHostController,
    authSessionViewModel: AuthSessionViewModel
) {
    val userEmail by authSessionViewModel.userEmail.collectAsState()
    val isAuthCheckDone by authSessionViewModel.isAuthCheckDone.collectAsState()

    // Controlador de animación Lottie
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splashanimation))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever // Repetir infinitamente
    )

    // Efecto para navegación
    LaunchedEffect(isAuthCheckDone) {
        if (isAuthCheckDone) {
            delay(1000L) // 1 segundo
            if (userEmail != null) {
                navController.navigate(Routes.Home.route) {
                    popUpTo(0) { inclusive = true }
                }
            } else {
                navController.navigate(Routes.Login.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    // Pantalla con animación
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(200.dp)
        )
    }
}