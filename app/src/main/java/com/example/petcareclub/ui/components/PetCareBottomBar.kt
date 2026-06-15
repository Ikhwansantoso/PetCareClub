package com.example.petcareclub.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petcareclub.ui.navigation.Screen

// Custom outlined gift icon vector
val GiftIcon: ImageVector
    get() = ImageVector.Builder(
        name = "GiftIcon",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        // Draw the box base (outline)
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f
        ) {
            moveTo(5f, 10f)
            lineTo(19f, 10f)
            lineTo(19f, 21f)
            lineTo(5f, 21f)
            close()
        }
        // Draw the lid (outline)
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f
        ) {
            moveTo(3f, 7f)
            lineTo(21f, 7f)
            lineTo(21f, 11f)
            lineTo(3f, 11f)
            close()
        }
        // Draw vertical ribbon
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f
        ) {
            moveTo(12f, 7f)
            lineTo(12f, 21f)
        }
        // Draw bow loops (loops at top)
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2f
        ) {
            // Left loop
            moveTo(12f, 7f)
            curveTo(10f, 3f, 6f, 3f, 12f, 7f)
            // Right loop
            curveTo(14f, 3f, 18f, 3f, 12f, 7f)
        }
    }.build()

@Composable
fun PetCareBottomBar(
    navController: NavController,
    currentRoute: String
) {
    // Floating rounded card container
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding() // keeps it above system navigation bar
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(28.dp))
            .background(Color.White, shape = RoundedCornerShape(28.dp))
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            val activeColor = Color(0xFF5F69C9)
            val inactiveColor = Color(0xFF6A729A)

            // Home Tab
            val homeSelected = currentRoute == Screen.Home.route
            NavigationBarItem(
                selected = homeSelected,
                onClick = {
                    if (currentRoute != Screen.Home.route) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                },
                icon = { 
                    Icon(
                        imageVector = if (homeSelected) Icons.Default.Home else Icons.Outlined.Home, 
                        contentDescription = "Home"
                    ) 
                },
                label = { Text("Home", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = activeColor,
                    selectedTextColor = activeColor,
                    unselectedIconColor = inactiveColor,
                    unselectedTextColor = inactiveColor,
                    indicatorColor = Color.Transparent
                )
            )

            // Riwayat Tab
            val riwayatSelected = currentRoute == Screen.Riwayat.route
            NavigationBarItem(
                selected = riwayatSelected,
                onClick = {
                    if (currentRoute != Screen.Riwayat.route) {
                        navController.navigate(Screen.Riwayat.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { 
                    Icon(
                        imageVector = if (riwayatSelected) Icons.Default.DateRange else Icons.Outlined.DateRange, 
                        contentDescription = "Riwayat"
                    ) 
                },
                label = { Text("Riwayat", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = activeColor,
                    selectedTextColor = activeColor,
                    unselectedIconColor = inactiveColor,
                    unselectedTextColor = inactiveColor,
                    indicatorColor = Color.Transparent
                )
            )

            // Reward Tab
            val rewardSelected = currentRoute == Screen.Reward.route
            NavigationBarItem(
                selected = rewardSelected,
                onClick = {
                    if (currentRoute != Screen.Reward.route) {
                        navController.navigate(Screen.Reward.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { 
                    Icon(
                        imageVector = GiftIcon, 
                        contentDescription = "Reward"
                    ) 
                },
                label = { Text("Reward", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = activeColor,
                    selectedTextColor = activeColor,
                    unselectedIconColor = inactiveColor,
                    unselectedTextColor = inactiveColor,
                    indicatorColor = Color.Transparent
                )
            )

            // Profil Tab
            val profileSelected = currentRoute == Screen.Profile.route
            NavigationBarItem(
                selected = profileSelected,
                onClick = {
                    if (currentRoute != Screen.Profile.route) {
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { 
                    Icon(
                        imageVector = if (profileSelected) Icons.Default.Person else Icons.Outlined.Person, 
                        contentDescription = "Profil"
                    ) 
                },
                label = { Text("Profil", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = activeColor,
                    selectedTextColor = activeColor,
                    unselectedIconColor = inactiveColor,
                    unselectedTextColor = inactiveColor,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
