package com.example.petcareclub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import com.example.petcareclub.data.local.database.AppDatabase
import com.example.petcareclub.data.repository.PetCareRepository
import com.example.petcareclub.ui.navigation.NavGraph
import com.example.petcareclub.ui.theme.PetCareClubTheme
import com.example.petcareclub.ui.viewmodel.PetCareViewModel
import com.example.petcareclub.ui.viewmodel.PetCareViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize AppDatabase & Repository
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = PetCareRepository(
            userDao = database.userDao(),
            petDao = database.petDao(),
            medicalRecordDao = database.medicalRecordDao(),
            voucherDao = database.voucherDao()
        )
        
        // Instantiate Shared ViewModel
        val viewModel: PetCareViewModel by viewModels {
            PetCareViewModelFactory(repository)
        }

        // Global status message toast observer
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.statusMessage.collect { msg ->
                    Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            PetCareClubTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}