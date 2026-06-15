package com.example.petcareclub.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object AddPet : Screen("add_pet")
    object DetailPet : Screen("detail_pet")
    object AddRecord : Screen("add_record")
    object Reward : Screen("reward")
    object Profile : Screen("profile")
    object MyVouchers : Screen("my_vouchers")
    object Riwayat : Screen("riwayat")
}
