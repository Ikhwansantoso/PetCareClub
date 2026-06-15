package com.example.petcareclub.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petcareclub.ui.screen.history.AddRecordScreen
import com.example.petcareclub.ui.screen.home.HomeScreen
import com.example.petcareclub.ui.screen.login.LoginScreen
import com.example.petcareclub.ui.screen.pet.AddPetScreen
import com.example.petcareclub.ui.screen.pet.DetailPetScreen
import com.example.petcareclub.ui.screen.profile.ProfileScreen
import com.example.petcareclub.ui.screen.reward.RewardScreen
import com.example.petcareclub.ui.screen.reward.MyVouchersScreen
import com.example.petcareclub.ui.screen.history.RiwayatScreen
import com.example.petcareclub.ui.viewmodel.PetCareViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: PetCareViewModel
) {
    val currentUserId by viewModel.currentUserId.collectAsState()
    val startDestination = if (currentUserId != null) Screen.Home.route else Screen.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.AddPet.route) {
            AddPetScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.DetailPet.route) {
            DetailPetScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.AddRecord.route) {
            AddRecordScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Reward.route) {
            RewardScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.MyVouchers.route) {
            MyVouchersScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Riwayat.route) {
            RiwayatScreen(navController = navController, viewModel = viewModel)
        }
    }
}
