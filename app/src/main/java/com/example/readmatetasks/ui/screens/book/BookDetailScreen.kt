import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.airbnb.lottie.compose.*
import com.example.readmatetasks.data.model.Book
import com.example.readmatetasks.ui.session.ProgressViewModel
import com.example.readmatetasks.ui.screens.book.BookDetailViewModel
import com.example.readmatetasks.ui.screens.timer.TimerViewModel
import com.example.readmatetasks.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    navController: NavController,
    progressViewModel: ProgressViewModel,
    bookDetailViewModel: BookDetailViewModel,
    timerViewModel: TimerViewModel,
    book: Book
) {
    val wishlist = progressViewModel.wishlist
    val isInWishlist = wishlist.contains(book.id)
    val context = LocalContext.current

    var dominantColor by remember { mutableStateOf(SecondAccent) }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Información", "Curiosidades")

    val curiosities by bookDetailViewModel.curiosities.collectAsState()
    val isLoading by bookDetailViewModel.isLoading.collectAsState()

    // Llama a Gemini solo una vez al cambiar a la pestaña Curiosities
    var hasFetchedCuriosities by remember { mutableStateOf(false) }

    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex == 1 && !hasFetchedCuriosities) {
            bookDetailViewModel.fetchCuriosities(book.title, book.author)
            hasFetchedCuriosities = true
        }
    }

    LaunchedEffect(book.coverImage) {
        val imageLoader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(book.coverImage)
            .allowHardware(false)
            .build()

        val result = (imageLoader.execute(request) as? SuccessResult)?.drawable
        val bitmap = (result as? BitmapDrawable)?.bitmap

        bitmap?.let {
            Palette.from(it).generate { palette ->
                palette?.dominantSwatch?.rgb?.let { colorValue ->
                    var extractedColor = Color(colorValue)
                    if (extractedColor.luminance() < 0.3f) {
                        extractedColor = extractedColor.copy(
                            red = (extractedColor.red + 1f) / 2f,
                            green = (extractedColor.green + 1f) / 2f,
                            blue = (extractedColor.blue + 1f) / 2f
                        )
                    }
                    dominantColor = extractedColor
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MainTitleColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = dominantColor)
            )
        },
        containerColor = BackgroundColor
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .height(300.dp)
                    .background(dominantColor)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        tonalElevation = 2.dp,
                        shadowElevation = 8.dp,
                        modifier = Modifier
                            .width(140.dp)
                            .height(220.dp)
                    ) {
                        Image(
                            painter = painterResource(id = book.coverImage),
                            contentDescription = book.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Top
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = book.title,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MainTitleColor
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = {
                                    if (isInWishlist) {
                                        progressViewModel.removeFromWishlist(book.id)
                                    } else {
                                        progressViewModel.addToWishlist(book.id)
                                    }
                                },
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    imageVector = if (isInWishlist) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                    contentDescription = "Bookmark",
                                    tint = AccentColor,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "${book.totalPages}",
                                fontSize = 14.sp,
                                color = AccentColor
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Filled.MenuBook,
                                contentDescription = "Pages",
                                tint = AccentColor,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // Card autor
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 260.dp)
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(24.dp),
                        ambientColor = Color.Black.copy(alpha = 0.15f),
                        spotColor = Color.Black.copy(alpha = 0.15f)
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Image(
                        painter = painterResource(id = book.authorImage),
                        contentDescription = "Autor",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Autor",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        Text(
                            text = book.author,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MainTitleColor
                        )
                    }
                }
            }

            // Tabs + contenido
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 370.dp, start = 16.dp, end = 16.dp)
            ) {
                // Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedTabIndex == index
                        val backgroundColor = if (isSelected) SecondAccent else Color.White
                        val textColor = if (isSelected) MainTitleColor else Color.Gray

                        TextButton(
                            onClick = { selectedTabIndex = index },
                            colors = ButtonDefaults.textButtonColors(containerColor = backgroundColor),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp)
                                .padding(horizontal = 1.dp)
                        ) {
                            Text(
                                text = title,
                                color = textColor,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                            )
                        }

                        if (index == 0) {
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                when (selectedTabIndex) {
                    0 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 200.dp, max = 400.dp) // ajusta según tus necesidades
                                .padding(horizontal = 10.dp, vertical = 16.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                text = "Sinopsis",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MainTitleColor
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = book.synopsis,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 17.sp,
                                    lineHeight = 26.sp,
                                    color = Color.Black
                                )
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = "Sobre este libro",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MainTitleColor
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = book.about,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 17.sp,
                                    lineHeight = 26.sp,
                                    color = Color.Black
                                )
                            )
                        }
                    }
                    1 -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 200.dp, max = 400.dp)
                        ) {
                            if (isLoading) {
                                val composition by rememberLottieComposition(
                                    LottieCompositionSpec.RawRes(
                                        com.example.readmatetasks.R.raw.loadingtext
                                    )
                                )
                                val progress by animateLottieCompositionAsState(composition)
                                LottieAnimation(
                                    composition = composition,
                                    progress = { progress },
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(150.dp)
                                )
                            } else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .verticalScroll(rememberScrollState())
                                        .padding(horizontal = 10.dp, vertical = 16.dp) // Ajusta aquí el padding deseado
                                ) {
                                    Text(
                                        text = curiosities,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontSize = 17.sp,
                                            lineHeight = 26.sp,
                                            color = Color.Black
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}