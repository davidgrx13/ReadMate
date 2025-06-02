package com.example.readmatetasks.ui.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readmatetasks.R
import com.example.readmatetasks.ui.theme.AccentColor
import com.example.readmatetasks.ui.theme.BackgroundColor
import com.example.readmatetasks.ui.theme.MainTitleColor
import com.example.readmatetasks.ui.theme.SecondAccent
import com.example.readmatetasks.utils.getBitmapFromUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    viewModel: EditProfileViewModel
) {
    val context = LocalContext.current
    val username by viewModel.username.collectAsState()
    val profileImage by viewModel.profileImage.collectAsState()

    var newUsername by remember { mutableStateOf(username ?: "") }
    var newPassword by remember { mutableStateOf("") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = getBitmapFromUri(context, it)
            if (bitmap != null) {
                viewModel.setProfileImage(context, bitmap)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchProfileImage(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(0.5f))
                        Text(
                            text = stringResource(id = R.string.profile_edit),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MainTitleColor,
                                fontSize = 22.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(2f)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = MainTitleColor // Aquí aplicas el color
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundColor)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .background(AccentColor.copy(alpha = 0.3f))
                    .clickable { imagePickerLauncher.launch("image/*") }
            ) {
                profileImage?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )
                }
            }
            OutlinedTextField(
                value = newUsername,
                onValueChange = { newUsername = it },
                label = { Text(stringResource(id = R.string.name)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .defaultMinSize(minHeight = 50.dp), // Cambiado para evitar recorte
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Gray,
                ),
                singleLine = true
            )
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text(stringResource(id = R.string.pass)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .defaultMinSize(minHeight = 50.dp), // Igual aquí
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedLabelColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Gray,
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    viewModel.updateUsername(newUsername)
                    if (newPassword.isNotEmpty()) {
                        viewModel.updatePassword(newPassword)
                    }
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = SecondAccent),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(vertical = 8.dp),
                modifier = Modifier
                    .width(280.dp)
                    .height(55.dp),
                enabled = newUsername.isNotBlank()
            ) {
                Text(
                    text = stringResource(id = R.string.save),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MainTitleColor
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
