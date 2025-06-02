package com.example.readmatetasks.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.readmatetasks.R
import com.example.readmatetasks.data.repository.DailyGoalRepository
import com.example.readmatetasks.data.repository.ReadingTimeRepository
import com.example.readmatetasks.ui.components.BottomNavBar
import com.example.readmatetasks.ui.components.UserCard
import com.example.readmatetasks.ui.navigation.Routes
import com.example.readmatetasks.ui.theme.BackgroundColor
import com.example.readmatetasks.ui.theme.MainTitleColor
import com.example.readmatetasks.ui.session.AuthSessionViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    authSessionViewModel: AuthSessionViewModel,
    logoutUser: () -> Unit,
    onEditProfile: () -> Unit,
    navController: NavController
) {
    val profileViewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(authSessionViewModel, DailyGoalRepository(), ReadingTimeRepository())
    )
    val user by authSessionViewModel.username.collectAsState()
    val context = LocalContext.current
    val currentRoute = navController.currentDestination?.route ?: Routes.Profile.route

    val coroutineScope = rememberCoroutineScope()
    var showGoalDialog by remember { mutableStateOf(false) }
    var inputGoal by remember { mutableStateOf("") }
    val currentGoal = profileViewModel.dailyGoal.collectAsState(initial = 0)

    // Fetch user data on load
    LaunchedEffect(Unit) {
        authSessionViewModel.fetchCurrentUser()
        authSessionViewModel.fetchProfileImage(context)
        profileViewModel.loadDailyGoal(context)
        profileViewModel.loadAverageReadingMinutes(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Perfil",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = MainTitleColor,
                            fontSize = 38.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp),
                        textAlign = TextAlign.Left
                    )
                },
                modifier = Modifier.background(BackgroundColor),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundColor)
            )
        },
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                navController = navController,
                modifier = Modifier
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(25.dp))
                UserCard(
                    name = user ?: stringResource(id = R.string.user_unknown),
                    email = authSessionViewModel.userEmail.collectAsState().value ?: "",
                    logoutUser = { profileViewModel.logout(); logoutUser() },
                    authSessionViewModel = authSessionViewModel
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Nada */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Average",
                            tint = MainTitleColor,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Media: ${profileViewModel.averageReadingMinutes.collectAsState().value} minutos por día",
                            color = MainTitleColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Edit Profile button
                Button(
                    onClick = onEditProfile,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit",
                            tint = MainTitleColor,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(id = R.string.profile_edit),
                            color = MainTitleColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Change Goal button
                Button(
                    onClick = { showGoalDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.EmojiEvents,
                            contentDescription = "Goal",
                            tint = MainTitleColor,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Cambiar tu meta diaria (${currentGoal.value} min)",
                            color = MainTitleColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    }
                }
            }
        }
    }

    if (showGoalDialog) {
        AlertDialog(
            onDismissRequest = { showGoalDialog = false },
            title = { Text("Set your daily reading goal (minutes)") },
            text = {
                OutlinedTextField(
                    value = inputGoal,
                    onValueChange = { inputGoal = it },
                    placeholder = { Text("Enter minutes") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val minutes = inputGoal.toIntOrNull()
                        if (minutes != null && minutes >= 0) {
                            coroutineScope.launch {
                                profileViewModel.saveDailyGoal(minutes, context)
                            }
                        }
                        showGoalDialog = false
                        inputGoal = ""
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showGoalDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
