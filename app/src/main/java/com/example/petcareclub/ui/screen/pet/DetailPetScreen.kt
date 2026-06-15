package com.example.petcareclub.ui.screen.pet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petcareclub.R
import com.example.petcareclub.data.local.entity.MedicalRecord
import com.example.petcareclub.data.local.entity.Pet
import com.example.petcareclub.ui.navigation.Screen
import com.example.petcareclub.ui.viewmodel.PetCareViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPetScreen(
    navController: NavController,
    viewModel: PetCareViewModel
) {
    val pet by viewModel.selectedPet.collectAsState()
    val records by viewModel.selectedPetRecords.collectAsState()

    val recommendation = remember(records) {
        if (records.isNotEmpty()) {
            val lastRecord = records.first()
            val treatmentLower = lastRecord.treatment.lowercase()
            when {
                treatmentLower.contains("grooming") -> "Rekomendasi Grooming berikutnya: 30 hari setelah ${lastRecord.date} ✂️"
                treatmentLower.contains("vaksin") -> "Rekomendasi Vaksinasi berikutnya: 1 tahun setelah ${lastRecord.date} 💉"
                treatmentLower.contains("check-up") || treatmentLower.contains("periksa") -> "Rekomendasi Pemeriksaan berikutnya: 6 bulan setelah ${lastRecord.date} 🩺"
                else -> "Rekomendasi Kunjungan rutin berikutnya: 3 bulan setelah ${lastRecord.date} 📅"
            }
        } else {
            null
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.login_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F6FA).copy(alpha = 0.9f))
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { 
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text("Detail Hewan", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = MaterialTheme.colorScheme.primary)
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Menu Lain", tint = MaterialTheme.colorScheme.primary)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
            pet?.let { currentPet ->
                val photoBitmap = remember(currentPet.photoPath) {
                    if (!currentPet.photoPath.isNullOrEmpty() && !currentPet.photoPath.startsWith("pet_")) {
                        val file = java.io.File(currentPet.photoPath)
                        if (file.exists()) {
                            android.graphics.BitmapFactory.decodeFile(file.absolutePath)
                        } else {
                            null
                        }
                    } else {
                        null
                    }
                }

                val seededDrawableId = when (currentPet.photoPath) {
                    "pet_mimi" -> R.drawable.pet_mimi
                    "pet_bobby" -> R.drawable.pet_bobby
                    "pet_luna" -> R.drawable.pet_luna
                    else -> null
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 32.dp)
                ) {
                    // Curved purple header & Pet Avatar Section (Screen 3)
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.primary,
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
                                        )
                                    ),
                                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                                )
                                .padding(top = 16.dp, bottom = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                // Circular frame with white border
                                Surface(
                                    modifier = Modifier.size(110.dp),
                                    shape = CircleShape,
                                    color = Color.White,
                                    shadowElevation = 4.dp
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        if (seededDrawableId != null) {
                                            Image(
                                                painter = painterResource(id = seededDrawableId),
                                                contentDescription = "Foto Hewan",
                                                modifier = Modifier.fillMaxSize().clip(CircleShape),
                                                contentScale = ContentScale.Crop
                                            )
                                        } else if (photoBitmap != null) {
                                            Image(
                                                bitmap = photoBitmap.asImageBitmap(),
                                                contentDescription = "Foto Hewan",
                                                modifier = Modifier.fillMaxSize().clip(CircleShape),
                                                contentScale = ContentScale.Crop
                                            )
                                        } else {
                                            val typeLower = currentPet.type.lowercase()
                                            val emoji = when {
                                                typeLower.contains("kucing") || typeLower.contains("cat") -> "🐱"
                                                typeLower.contains("anjing") || typeLower.contains("dog") -> "🐶"
                                                typeLower.contains("kelinci") || typeLower.contains("rabbit") -> "🐰"
                                                typeLower.contains("burung") || typeLower.contains("bird") -> "🐦"
                                                typeLower.contains("hamster") -> "🐹"
                                                else -> "🐾"
                                            }
                                            Text(text = emoji, fontSize = 56.sp)
                                        }
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                Text(
                                    text = currentPet.name,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = currentPet.type,
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                                Text(
                                    text = "Umur ${currentPet.age}",
                                    fontSize = 13.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }

                    // Pet Information Grid Card (Screen 3)
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(
                                    text = "Informasi",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                                
                                InfoTableRow(label = "Jenis Hewan", value = currentPet.type)
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                                InfoTableRow(label = "Umur Hewan", value = currentPet.age)
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                                InfoTableRow(label = "Berat Hewan", value = currentPet.weight)
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                                InfoTableRow(label = "ID Registrasi", value = "PET-${String.format("%04d", currentPet.id)}")
                            }
                        }
                    }

                    // Dynamic next appointment recommendation banner
                    recommendation?.let { text ->
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.12f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = "💡", fontSize = 20.sp)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = text,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }

                    // Riwayat Kesehatan Header Row
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Riwayat Kesehatan",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            
                            // + Tambah button (Mockup Screen 3)
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                                modifier = Modifier.clickable { navController.navigate(Screen.AddRecord.route) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Add,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Tambah",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }

                    // Riwayat Kesehatan Lists
                    if (records.isEmpty()) {
                        item {
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                EmptyRecordsState()
                            }
                        }
                    } else {
                        items(records) { record ->
                            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                                MedicalRecordCard(record = record)
                            }
                        }
                    }
                }
            } ?: Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Data hewan tidak ditemukan.", fontSize = 16.sp)
            }
        }
    }
}
}

@Composable
fun InfoTableRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun MedicalRecordCard(record: MedicalRecord) {
    val treatmentLower = record.treatment.lowercase()
    val (iconText, iconColor) = when {
        treatmentLower.contains("vaksin") || treatmentLower.contains("vaccine") -> "💉" to Color(0xFF5F69C9)
        treatmentLower.contains("grooming") || treatmentLower.contains("mandi") -> "✂️" to Color(0xFFF59E0B)
        treatmentLower.contains("check-up") || treatmentLower.contains("periksa") -> "🩺" to Color(0xFF10B981)
        else -> "📋" to MaterialTheme.colorScheme.primary
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = iconColor.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = iconText, fontSize = 22.sp)
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = record.treatment,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = record.date,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }
            Text(
                text = "➜",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        }
    }
}

@Composable
fun EmptyRecordsState() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "🛡️", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Belum Ada Riwayat Kesehatan",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
            Text(
                text = "Tekan tombol '+ Tambah' di atas untuk mencatatkan kunjungan baru.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
