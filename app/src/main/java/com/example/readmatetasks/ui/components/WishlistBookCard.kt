package com.example.readmatetasks.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readmatetasks.data.model.Book
import com.example.readmatetasks.ui.theme.AccentColor
import com.example.readmatetasks.ui.theme.MainTitleColor
import com.example.readmatetasks.ui.theme.SecondAccent

@Composable
fun WishlistBookCard(
    book: Book,
    currentPage: Int,
    onUpdateProgress: (Int) -> Unit,
    onRemove: () -> Unit,
    onClickCard: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var inputPage by remember { mutableStateOf("") }
    var localProgress by remember { mutableStateOf(currentPage) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .width(400.dp)
            .padding(8.dp)
            .shadow(
                elevation = 32.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Black.copy(alpha = 0.2f),
                spotColor = Color.Black.copy(alpha = 0.1f)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .clickable { onClickCard() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                tonalElevation = 2.dp,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .width(75.dp)
                    .height(120.dp)
            ) {
                Image(
                    painter = painterResource(id = book.coverImage),
                    contentDescription = book.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = book.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MainTitleColor,
                        maxLines = 2
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${book.totalPages}",
                            fontSize = 12.sp,
                            color = AccentColor
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Filled.MenuBook,
                            contentDescription = "Pages",
                            tint = AccentColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Text(
                    text = book.author,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))

                val progress = (localProgress.toFloat() / book.totalPages.toFloat()).coerceIn(0f, 1f)

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        progress = progress,
                        modifier = Modifier
                            .weight(1f)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = AccentColor,
                        trackColor = SecondAccent
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { showDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = SecondAccent),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(35.dp)
                    ) {
                        Text(
                            text = "Progreso",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MainTitleColor
                        )
                    }
                    Button(
                        onClick = onRemove,
                        colors = ButtonDefaults.buttonColors(containerColor = SecondAccent),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(35.dp)
                    ) {
                        Text(
                            text = "Eliminar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MainTitleColor
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Actualizar progreso") },
            text = {
                Column {
                    Text("Página actual:")
                    OutlinedTextField(
                        value = inputPage,
                        onValueChange = { inputPage = it },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val page = inputPage.toIntOrNull()
                        if (page != null && page in 0..book.totalPages) {
                            onUpdateProgress(page)
                            localProgress = page
                        }
                        showDialog = false
                        inputPage = ""
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}