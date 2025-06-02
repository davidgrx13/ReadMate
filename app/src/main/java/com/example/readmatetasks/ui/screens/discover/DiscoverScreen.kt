package com.example.readmatetasks.ui.screens.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readmatetasks.data.repository.BookRepository
import com.example.readmatetasks.ui.components.BookCard
import com.example.readmatetasks.ui.components.BottomNavBar
import com.example.readmatetasks.ui.navigation.Routes
import com.example.readmatetasks.ui.session.ProgressViewModel
import com.example.readmatetasks.ui.theme.BackgroundColor
import com.example.readmatetasks.ui.theme.MainTitleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(
    navController: NavController,
    progressViewModel: ProgressViewModel
) {
    val currentRoute = navController.currentDestination?.route ?: Routes.Discover.route
    val wishlist = progressViewModel.wishlist

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // Filtrar libros según el texto
    val filteredBooks = remember(searchQuery.text) {
        if (searchQuery.text.isBlank()) {
            BookRepository.books
        } else {
            BookRepository.books.filter {
                it.title.contains(searchQuery.text, ignoreCase = true) ||
                        it.author.contains(searchQuery.text, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Descubrir",
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
                .background(BackgroundColor)
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // SearchBar con icono de lupa
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.medium)
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.Gray,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (searchQuery.text.isEmpty()) {
                                Text("Título, autor...", color = Color.Gray)
                            }
                            innerTextField()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Librería",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(horizontal = 28.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(filteredBooks, key = { it.id }) { book ->
                    val isInWishlist = wishlist.contains(book.id)
                    BookCard(
                        book = book,
                        isInWishlist = isInWishlist,
                        onBookmarkClick = {
                            if (isInWishlist) {
                                progressViewModel.removeFromWishlist(book.id)
                            } else {
                                progressViewModel.addToWishlist(book.id)
                            }
                        },
                        onCardClick = {
                            navController.navigate("${Routes.BookDetail.route}/${book.id}")
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(85.dp))
                }
            }

        }
    }
}