package com.example.readmatetasks.ui.screens.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readmatetasks.data.repository.BookRepository
import com.example.readmatetasks.ui.components.BottomNavBar
import com.example.readmatetasks.ui.components.WishlistBookCard
import com.example.readmatetasks.ui.navigation.Routes
import com.example.readmatetasks.ui.session.ProgressViewModel
import com.example.readmatetasks.ui.theme.BackgroundColor
import com.example.readmatetasks.ui.theme.MainTitleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(
    navController: NavController,
    progressViewModel: ProgressViewModel
) {
    val currentRoute = Routes.Wishlist.route
    val wishlist = progressViewModel.wishlist
    val progressMap by progressViewModel.progressMap.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Biblioteca",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .background(BackgroundColor)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(0.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
            ) {
                item {
                    // Botón para ver libros leídos
                    Button(
                        onClick = { navController.navigate(Routes.FinishedBooks.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "Libros terminados",
                                tint = MainTitleColor,
                                modifier = Modifier.size(26.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Libros terminados",
                                color = MainTitleColor,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    // Botón para ver libros pendientes
                    Button(
                        onClick = { navController.navigate(Routes.WishlistPending.route) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                Icons.Outlined.BookmarkBorder,
                                contentDescription = "Want to read Books",
                                tint = MainTitleColor,
                                modifier = Modifier.size(26.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Lista de deseos",
                                color = MainTitleColor,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize
                            )
                        }
                    }
                }

                // Mostrar solo los libros no terminados
                items(wishlist, key = { it }) { bookId ->
                    val book = BookRepository.getBookById(bookId)
                    if (book != null) {
                        val currentProgress = progressMap[bookId] ?: 0
                        if (currentProgress in 1 until book.totalPages) {
                            WishlistBookCard(
                                book = book,
                                currentPage = currentProgress,
                                onUpdateProgress = { newPage ->
                                    progressViewModel.updateProgress(bookId, newPage)
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
                item {
                    Spacer(modifier = Modifier.height(85.dp))
                }
            }
        }
    }
}