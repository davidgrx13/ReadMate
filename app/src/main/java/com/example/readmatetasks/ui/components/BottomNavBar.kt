package com.example.readmatetasks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readmatetasks.R
import com.example.readmatetasks.ui.navigation.Routes
import com.example.readmatetasks.ui.theme.*

@Composable
fun BottomNavBar(
    currentRoute: String,
    navController: NavController,
    modifier: Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Línea superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.7.dp)
                .background(Color.LightGray.copy(alpha = 0.4f))
        )

        // NavigationBar normal
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp),
            containerColor = BackgroundColor.copy(alpha = 0.95f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val items = listOf(
                    NavItem("Home", R.drawable.home, Routes.Home.route),
                    NavItem("Discover", R.drawable.discover, Routes.Discover.route),
                    NavItem("Timer", R.drawable.timmer, "timer"),
                    NavItem("Wishlist", R.drawable.wishlist, "wishlist"),
                    NavItem("Profile", R.drawable.user, Routes.Profile.route)
                )

                items.forEach { item ->
                    val isSelected = currentRoute == item.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (navController.currentDestination?.route != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(0)
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(32))
                                    .background(if (isSelected) SecondAccent else Color.Transparent),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.label,
                                    tint = if (isSelected) AccentColor else DarkGray
                                )
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        ),
                        alwaysShowLabel = false
                    )
                }
            }
        }
    }
}

data class NavItem(val label: String, val icon: Int, val route: String)