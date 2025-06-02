package com.example.readmatetasks.ui.screens.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readmatetasks.ui.components.WishlistBookCard
import com.example.readmatetasks.ui.session.ProgressViewModel
import com.example.readmatetasks.ui.theme.BackgroundColor
import com.example.readmatetasks.ui.theme.MainTitleColor
import com.example.readmatetasks.data.repository.BookRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinishedBooksScreen(
    navController: NavController,
    progressViewModel: ProgressViewModel
) {
    val wishlist = progressViewModel.wishlist
    val progressMap by progressViewModel.progressMap.collectAsState()

    val finishedBooks = wishlist.mapNotNull { bookId ->
        val book = BookRepository.getBookById(bookId)
        val currentPage = progressMap[bookId] ?: 0
        if (book != null && currentPage >= book.totalPages) Pair(book, currentPage) else null
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
                            text = "Finished Books",
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
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
            ) {
                items(finishedBooks.size) { index ->
                    val (book, currentPage) = finishedBooks[index]
                    WishlistBookCard(
                        book = book,
                        currentPage = currentPage,
                        onUpdateProgress = { newPage ->
                            progressViewModel.updateProgress(book.id, newPage)
                        },
                        onRemove = {}, // Opcional: permitir eliminar si quieres
                        onClickCard = {
                            navController.navigate("book_detail/${book.id}")
                        }
                    )
                }
            }
        }
    }
}
