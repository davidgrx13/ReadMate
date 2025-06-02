package com.example.readmatetasks.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.readmatetasks.R
import com.example.readmatetasks.ui.theme.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.readmatetasks.ui.session.AuthSessionViewModel

@Composable
fun UserCard(
    name: String,
    email: String,
    logoutUser: () -> Unit,
    authSessionViewModel: AuthSessionViewModel
) {
    val profileImage by authSessionViewModel.profileImage.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Círculo alrededor de la imagen
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .border(3.dp, SecondAccent, CircleShape)
                ) {
                    profileImage?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = stringResource(id = R.string.user_pic),
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                    } ?: Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = stringResource(id = R.string.user_pic),
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = MainTitleColor
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = FontWeight.Normal,
                            color = MainTitleColor
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Button(
                onClick = logoutUser,
                colors = ButtonDefaults.buttonColors(containerColor = SecondAccent)
            ) {
                Text(
                    text = stringResource(id = R.string.logout),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        color = MainTitleColor
                    )
                )
            }
        }
    }
}