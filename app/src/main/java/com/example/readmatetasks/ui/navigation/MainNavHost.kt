package com.example.readmatetasks.ui.navigation

import BookDetailScreen
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.readmatetasks.data.repository.AuthRepository
import com.example.readmatetasks.data.repository.BookRepository
import com.example.readmatetasks.data.repository.DailyGoalRepository
import com.example.readmatetasks.data.repository.GeminiRepository
import com.example.readmatetasks.data.repository.ReadingTimeRepository
import com.example.readmatetasks.ui.screens.book.BookDetailViewModel
import com.example.readmatetasks.ui.screens.book.BookDetailViewModelFactory
import com.example.readmatetasks.ui.screens.home.HomeScreen
import com.example.readmatetasks.ui.screens.login.LoginScreen
import com.example.readmatetasks.ui.screens.register.RegisterScreen
import com.example.readmatetasks.ui.session.AuthSessionViewModel
import com.example.readmatetasks.ui.session.AuthSessionViewModelFactory
import com.example.readmatetasks.ui.screens.home.HomeViewModel
import com.example.readmatetasks.ui.screens.home.HomeViewModelFactory
import com.example.readmatetasks.ui.screens.profile.EditProfileScreen
import com.example.readmatetasks.ui.screens.profile.EditProfileViewModel
import com.example.readmatetasks.ui.screens.profile.EditProfileViewModelFactory
import com.example.readmatetasks.ui.screens.profile.ProfileScreen
import com.example.readmatetasks.ui.screens.profile.ProfileViewModel
import com.example.readmatetasks.ui.screens.profile.ProfileViewModelFactory
import com.example.readmatetasks.ui.session.ProgressViewModel
import com.example.readmatetasks.ui.session.ProgressViewModelFactory
import com.example.readmatetasks.ui.screens.discover.DiscoverScreen
import com.example.readmatetasks.ui.screens.splash.SplashScreen
import com.example.readmatetasks.ui.screens.timer.TimerScreen
import com.example.readmatetasks.ui.screens.timer.TimerViewModel
import com.example.readmatetasks.ui.screens.timer.TimerViewModelFactory
import com.example.readmatetasks.ui.screens.wishlist.FinishedBooksScreen
import com.example.readmatetasks.ui.screens.wishlist.WishlistPendingScreen
import com.example.readmatetasks.ui.screens.wishlist.WishlistScreen

@Composable
fun MainNavHost(navController: NavHostController) {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    val authSessionViewModel: AuthSessionViewModel = viewModel(
        factory = AuthSessionViewModelFactory(AuthRepository())
    )

    val geminiRepository = GeminiRepository()

    LaunchedEffect(Unit) {
        authSessionViewModel.fetchCurrentUser()
    }

    val userEmail by authSessionViewModel.userEmail.collectAsStateWithLifecycle()
    val userId = authSessionViewModel.getCurrentUserId() ?: ""

    val dailyGoalRepository = DailyGoalRepository()
    val readingTimeRepository = ReadingTimeRepository()

    val progressViewModel: ProgressViewModel = viewModel(
        factory = ProgressViewModelFactory(application, userId)
    )

    val timerViewModel: TimerViewModel? = if (userId.isNotEmpty()) {
        viewModel(
            key = userId,
            factory = TimerViewModelFactory(
                dailyGoalRepository,
                readingTimeRepository,
                context = context,
                userId = userId
            )
        )
    } else {
        null
    }

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {
        composable(Routes.Splash.route) {
            SplashScreen(
                navController = navController,
                authSessionViewModel = authSessionViewModel
            )
        }

        composable(Routes.Login.route) {
            LoginScreen(
                onLogin = {
                    val newUserId = authSessionViewModel.getCurrentUserId() ?: ""
                    progressViewModel.refreshForNewUser(newUserId)
                    navController.navigate(Routes.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onRegister = { navController.navigate(Routes.Register.route) }
            )
        }

        composable(Routes.Register.route) {
            RegisterScreen(
                onRegister = {
                    val newUserId = authSessionViewModel.getCurrentUserId() ?: ""
                    progressViewModel.refreshForNewUser(newUserId)
                    navController.navigate(Routes.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onBack = { navController.navigateUp() }
            )
        }

        composable(Routes.Home.route) {
            if (userId.isNotEmpty()) {
                val homeViewModel: HomeViewModel = viewModel(
                    key = userId,
                    factory = HomeViewModelFactory(
                        dailyGoalRepository = dailyGoalRepository,
                        readingTimeRepository = readingTimeRepository,
                        context = context,
                        userId = userId
                    )
                )

                timerViewModel?.let { timerVm ->
                    HomeScreen(
                        homeViewModel = homeViewModel,
                        authSessionViewModel = authSessionViewModel,
                        navController = navController,
                        timerViewModel = timerVm,
                        progressViewModel = progressViewModel
                    )
                }
            }
        }

        composable(Routes.EditProfile.route) {
            val editProfileViewModel = viewModel<EditProfileViewModel>(
                factory = EditProfileViewModelFactory(authSessionViewModel)
            )

            EditProfileScreen(
                navController = navController,
                viewModel = editProfileViewModel,
            )
        }

        composable(Routes.Profile.route) {
            val profileViewModel: ProfileViewModel = viewModel(
                factory = ProfileViewModelFactory(authSessionViewModel, dailyGoalRepository, readingTimeRepository)
            )

            ProfileScreen(
                authSessionViewModel = authSessionViewModel,
                logoutUser = {
                    authSessionViewModel.logout()
                    navController.navigate(Routes.Splash.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onEditProfile = { navController.navigate(Routes.EditProfile.route) },
                navController = navController
            )
        }

        composable(Routes.Discover.route) {
            DiscoverScreen(
                navController = navController,
                progressViewModel = progressViewModel
            )
        }

        composable(Routes.Timer.route) {
            timerViewModel?.let { timerVm ->
                TimerScreen(
                    navController = navController,
                    timerViewModel = timerVm,
                    progressViewModel = progressViewModel
                )
            }
        }

        composable(Routes.Wishlist.route) {
            WishlistScreen(
                navController = navController,
                progressViewModel = progressViewModel
            )
        }

        composable(Routes.FinishedBooks.route) {
            FinishedBooksScreen(navController = navController, progressViewModel = progressViewModel)
        }

        composable(Routes.WishlistPending.route) {
            WishlistPendingScreen(
                navController = navController,
                progressViewModel = progressViewModel
            )
        }

        composable("${Routes.BookDetail.route}/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: ""
            val book = BookRepository.getBookById(bookId)

            if (book != null) {
                val viewModel: BookDetailViewModel = viewModel(
                    factory = BookDetailViewModelFactory(geminiRepository)
                )

                timerViewModel?.let { timerVm ->
                    BookDetailScreen(
                        navController = navController,
                        progressViewModel = progressViewModel,
                        book = book,
                        bookDetailViewModel = viewModel,
                        timerViewModel = timerVm // Pasamos el TimerViewModel
                    )
                }
            }
        }
    }
}