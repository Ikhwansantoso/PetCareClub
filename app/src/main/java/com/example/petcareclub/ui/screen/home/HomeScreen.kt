package com.example.petcareclub.ui.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.example.petcareclub.data.local.entity.Pet
import com.example.petcareclub.data.local.entity.User
import com.example.petcareclub.ui.components.PetCareBottomBar
import com.example.petcareclub.ui.navigation.Screen
import com.example.petcareclub.ui.util.QrCodeGenerator
import com.example.petcareclub.ui.viewmodel.PetCareViewModel

// Helper extension to strip emojis and starry boxes from name
fun String.removeEmojis(): String {
    return this.replace(Regex("[\\p{So}\\p{Cn}🌌✨🐾🎁🎟️⭐🐶🐱🐰🐦🐟🐹👋☀️]"), "").trim()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: PetCareViewModel
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val userPets by viewModel.userPets.collectAsState()

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
                .background(Color(0xFFF4F6FA).copy(alpha = 0.92f))
        )

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                PetCareBottomBar(
                    navController = navController,
                    currentRoute = Screen.Home.route
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
            ) {
                if (currentUser != null) {
                    WelcomeHeader(user = currentUser!!)
                } else {
                    GuestWelcomeHeader(onRegisterClick = { navController.navigate(Screen.Profile.route) })
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (currentUser != null) {
                    PointsCard(user = currentUser!!)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Hewan Peliharaan Anda",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E244B),
                        modifier = Modifier.weight(1f)
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    OutlinedButton(
                        onClick = { navController.navigate(Screen.AddPet.route) },
                        shape = RoundedCornerShape(18.dp),
                        border = BorderStroke(1.dp, Color(0xFF5F69C9)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF5F69C9)
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(12.dp),
                                tint = Color(0xFF5F69C9)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Tambah",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                if (userPets.isEmpty()) {
                    EmptyPetsState()
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(bottom = 24.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(userPets) { pet ->
                            PetItemCard(
                                pet = pet,
                                onClick = {
                                    viewModel.selectPet(pet.id)
                                    navController.navigate(Screen.DetailPet.route)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeHeader(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Halo, ${user.name.removeEmojis()} 👋",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E244B)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Selamat datang kembali!",
                fontSize = 14.sp,
                color = Color(0xFF6A729A)
            )
        }

        val photoBitmap = remember(user.photoPath) {
            if (!user.photoPath.isNullOrEmpty()) {
                val file = java.io.File(user.photoPath)
                if (file.exists()) {
                    android.graphics.BitmapFactory.decodeFile(file.absolutePath)
                } else {
                    null
                }
            } else {
                null
            }
        }
        val isIkhwan = user.name.equals("Ikhwan", ignoreCase = true)

        Box(
            modifier = Modifier.size(56.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, Color(0xFFE8EAF6), CircleShape)
                    .align(Alignment.Center)
            ) {
                if (photoBitmap != null) {
                    Image(
                        bitmap = photoBitmap.asImageBitmap(),
                        contentDescription = "Foto Profil",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (isIkhwan) {
                    Image(
                        painter = painterResource(id = R.drawable.avatar_ikhwan),
                        contentDescription = "Foto Profil",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF5F69C9).copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.name.removeEmojis().take(1).uppercase(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5F69C9)
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(Color(0xFFE57373), shape = CircleShape)
                    .border(1.5.dp, Color.White, CircleShape)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
fun PointsCard(user: User) {
    val qrBitmap = remember(user) {
        val qrText = "PETCARE-MEMBER-${String.format("%04d", user.id)}"
        QrCodeGenerator.generateQrCode(qrText, 250)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF5F69C9),
                            Color(0xFF434EA8)
                        )
                    )
                )
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Total Poin Anda",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${user.points}",
                        color = Color.White,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFFFFA726), shape = CircleShape)
                            .border(1.5.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Card(
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (qrBitmap != null) {
                            Image(
                                bitmap = qrBitmap.asImageBitmap(),
                                contentDescription = "QR Code",
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize().background(Color.LightGray)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Tunjukkan QR\nsaat check-up",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 9.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 12.sp
                )
            }
        }
    }
}

@Composable
fun PetItemCard(pet: Pet, onClick: () -> Unit) {
    val typeLower = pet.type.lowercase()
    val emoji = when {
        typeLower.contains("kucing") || typeLower.contains("cat") -> "🐱"
        typeLower.contains("anjing") || typeLower.contains("dog") -> "🐶"
        typeLower.contains("kelinci") || typeLower.contains("rabbit") -> "🐰"
        typeLower.contains("burung") || typeLower.contains("bird") -> "🐦"
        typeLower.contains("ikan") || typeLower.contains("fish") -> "🐟"
        typeLower.contains("hamster") -> "🐹"
        else -> "🐾"
    }

    val photoBitmap = remember(pet.photoPath) {
        if (!pet.photoPath.isNullOrEmpty() && !pet.photoPath.startsWith("pet_")) {
            val file = java.io.File(pet.photoPath)
            if (file.exists()) {
                android.graphics.BitmapFactory.decodeFile(file.absolutePath)
            } else {
                null
            }
        } else {
            null
        }
    }

    val seededDrawableId = when (pet.photoPath) {
        "pet_mimi" -> R.drawable.pet_mimi
        "pet_bobby" -> R.drawable.pet_bobby
        "pet_luna" -> R.drawable.pet_luna
        else -> null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color(0xFFE8EAF6))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.12f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (seededDrawableId != null) {
                    Image(
                        painter = painterResource(id = seededDrawableId),
                        contentDescription = "Foto Hewan",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else if (photoBitmap != null) {
                    Image(
                        bitmap = photoBitmap.asImageBitmap(),
                        contentDescription = "Foto Hewan",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = emoji,
                        fontSize = 38.sp
                    )
                }
            }
            Spacer(modifier = Modifier.width(18.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = pet.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = pet.type,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Umur ${pet.age}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFF6A729A).copy(alpha = 0.6f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun EmptyPetsState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📭",
            fontSize = 56.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Belum Ada Hewan Peliharaan",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Silakan klik tombol '+ Tambah Hewan' di atas untuk mencatat data hewan.",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

@Composable
fun GuestWelcomeHeader(onRegisterClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Halo, Tamu Klinik! 👋",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E244B)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Selamat datang kembali!",
                fontSize = 14.sp,
                color = Color(0xFF6A729A)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Daftar Member Sekarang! ➜",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5F69C9),
                modifier = Modifier.clickable { onRegisterClick() }
            )
        }
        
        Box(
            modifier = Modifier
                .size(52.dp)
                .background(Color(0xFF5F69C9).copy(alpha = 0.1f), shape = CircleShape)
                .border(1.5.dp, Color(0xFF5F69C9).copy(alpha = 0.3f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "G",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5F69C9)
            )
        }
    }
}
