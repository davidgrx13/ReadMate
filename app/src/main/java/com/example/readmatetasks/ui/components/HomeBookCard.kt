package com.example.readmatetasks.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.readmatetasks.data.model.Book
import com.example.readmatetasks.ui.theme.AccentColor
import com.example.readmatetasks.ui.theme.MainTitleColor

@Composable
fun HomeBookCard(
    book: Book,
    onClickCard: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(140.dp)

            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Black.copy(alpha = 0.2f),
                spotColor = Color.Black.copy(alpha = 0.1f)
            )
            .graphicsLayer { alpha = 0.85f }
            .clickable { onClickCard() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 12.dp),
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
                    .shadow(
                        elevation = 8.dp,
                        shape = MaterialTheme.shapes.large,
                        ambientColor = AccentColor.copy(alpha = 0.5f),
                        spotColor = AccentColor.copy(alpha = 0.45f)
                    ),
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
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sugerencia:",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = book.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = MainTitleColor,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = book.author,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}