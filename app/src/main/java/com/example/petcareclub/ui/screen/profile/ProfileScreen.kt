package com.example.petcareclub.ui.screen.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.petcareclub.R
import com.example.petcareclub.ui.components.*
import com.example.petcareclub.ui.navigation.Screen
import com.example.petcareclub.ui.util.QrCodeGenerator
import com.example.petcareclub.ui.viewmodel.PetCareViewModel

@Composable
fun SmallStarCoin(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFFFFA726), shape = CircleShape)
            .border(1.dp, Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.fillMaxSize(0.6f)
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    titleColor: Color = Color(0xFF1E244B),
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(Color(0xFFEEF0FA), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (titleColor == Color(0xFFFF8A80)) Color(0xFFFF8A80) else Color(0xFF5F69C9),
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor
            )
        }
        Icon(
            imageVector = ChevronRightIcon,
            contentDescription = null,
            tint = Color(0xFF6A729A).copy(alpha = 0.6f),
            modifier = Modifier.size(16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: PetCareViewModel
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var showAboutDialog by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val file = java.io.File(context.filesDir, "profile_photo_${System.currentTimeMillis()}.jpg")
                val outputStream = java.io.FileOutputStream(file)
                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                viewModel.updateUserProfilePhoto(file.absolutePath)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Gagal memuat gambar!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Generate QR code dynamically when user changes
    val qrBitmap by remember(currentUser) {
        derivedStateOf {
            currentUser?.let { user ->
                val qrText = "PETCARE-MEMBER-${String.format("%04d", user.id)}"
                QrCodeGenerator.generateQrCode(qrText, 400)
            }
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
                CenterAlignedTopAppBar(
                    title = { 
                        Text(
                            text = "Profil Saya", 
                            fontWeight = FontWeight.Bold, 
                            fontSize = 18.sp,
                            color = Color(0xFF1E244B)
                        )
                    },
                    actions = {
                        IconButton(onClick = { Toast.makeText(context, "Fitur Pengaturan (Simulasi)", Toast.LENGTH_SHORT).show() }) {
                            Icon(
                                imageVector = Icons.Default.Settings, 
                                contentDescription = "Pengaturan", 
                                tint = Color(0xFF1E244B)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            bottomBar = {
                PetCareBottomBar(
                    navController = navController,
                    currentRoute = Screen.Profile.route
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                currentUser?.let { user ->
                    Spacer(modifier = Modifier.height(8.dp))

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

                    // Profile Info Card (Lavender Gradient)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFFE8EAF6),
                                            Color(0xFFD2D6FC)
                                        )
                                    )
                                )
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Clickable Profile Avatar Circular Frame
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.White, CircleShape)
                                    .clickable { imagePickerLauncher.launch("image/*") }
                            ) {
                                if (photoBitmap != null) {
                                    Image(
                                        bitmap = photoBitmap.asImageBitmap(),
                                        contentDescription = "Avatar Custom",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.avatar_ikhwan),
                                        contentDescription = "Avatar Ikhwan",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            Column {
                                Text(
                                    text = user.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1E244B)
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = user.email,
                                    fontSize = 13.sp,
                                    color = Color(0xFF6A729A)
                                )
                                
                                Spacer(modifier = Modifier.height(8.dp))

                                // Member level badge with crown
                                val memberLevel = if (user.points >= 100) "Gold Member" else if (user.points >= 50) "Silver Member" else "Bronze Member"
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color.White
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "👑", fontSize = 11.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = memberLevel,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1E244B)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Total Poin Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Total Poin",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF6A729A)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${user.points}",
                                    fontSize = 38.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF1E244B)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                SmallStarCoin(modifier = Modifier.size(24.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // QR Member Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "QR Member",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF6A729A)
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))

                            qrBitmap?.let { bitmap ->
                                Box(
                                    modifier = Modifier
                                        .size(170.dp)
                                        .background(Color.White, shape = RoundedCornerShape(16.dp))
                                        .border(1.dp, Color(0xFFE8EAF6), RoundedCornerShape(16.dp))
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        bitmap = bitmap.asImageBitmap(),
                                        contentDescription = "QR Code Member",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "ID Member",
                                fontSize = 11.sp,
                                color = Color(0xFF6A729A)
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "PC2024-${String.format("%04d", user.id)}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E244B)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Menu List Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .shadow(elevation = 4.dp, shape = RoundedCornerShape(24.dp)),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ProfileMenuItem(
                                icon = PawIcon,
                                title = "Hewan Peliharaan",
                                onClick = { navController.navigate(Screen.Home.route) }
                            )
                            HorizontalDivider(color = Color(0xFFE8EAF6), thickness = 1.dp)

                            ProfileMenuItem(
                                icon = ClipboardIcon,
                                title = "Riwayat Kesehatan",
                                onClick = { navController.navigate(Screen.Riwayat.route) }
                            )
                            HorizontalDivider(color = Color(0xFFE8EAF6), thickness = 1.dp)

                            ProfileMenuItem(
                                icon = GiftIconProfile,
                                title = "My Rewards",
                                onClick = { navController.navigate(Screen.MyVouchers.route) }
                            )
                            HorizontalDivider(color = Color(0xFFE8EAF6), thickness = 1.dp)

                            ProfileMenuItem(
                                icon = Icons.Default.Settings,
                                title = "Pengaturan",
                                onClick = { Toast.makeText(context, "Fitur Pengaturan (Simulasi)", Toast.LENGTH_SHORT).show() }
                            )
                            HorizontalDivider(color = Color(0xFFE8EAF6), thickness = 1.dp)

                            ProfileMenuItem(
                                icon = InfoIcon,
                                title = "Tentang Aplikasi",
                                onClick = { showAboutDialog = true }
                            )
                            HorizontalDivider(color = Color(0xFFE8EAF6), thickness = 1.dp)

                            ProfileMenuItem(
                                icon = LogoutIcon,
                                title = "Keluar / Ganti Akun",
                                titleColor = Color(0xFFFF8A80),
                                onClick = {
                                    viewModel.logout()
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(48.dp))
                } ?: Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var regName by remember { mutableStateOf("") }
                    var regEmail by remember { mutableStateOf("") }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Daftar Member Klinik",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E244B),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Gabung sekarang untuk mengaktifkan kartu digital, mengumpulkan poin, dan menukarkan hadiah menarik!",
                                fontSize = 12.sp,
                                color = Color(0xFF6A729A),
                                textAlign = TextAlign.Center,
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(24.dp))

                            OutlinedTextField(
                                value = regName,
                                onValueChange = { regName = it },
                                label = { Text("Nama Lengkap") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF5F69C9),
                                    unfocusedBorderColor = Color(0xFFE8EAF6)
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            OutlinedTextField(
                                value = regEmail,
                                onValueChange = { regEmail = it },
                                label = { Text("Alamat Email") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF5F69C9),
                                    unfocusedBorderColor = Color(0xFFE8EAF6)
                                )
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Button(
                                onClick = {
                                    viewModel.registerUser(regName, regEmail) { success -> }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F69C9))
                            ) {
                                Text(
                                    text = "Daftar & Aktifkan Member 🎁",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Logout Button for Guest
                    OutlinedButton(
                        onClick = {
                            viewModel.logout()
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFFF8A80)
                        ),
                        border = BorderStroke(1.5.dp, Color(0xFFFF8A80))
                    ) {
                        Text(
                            text = "Keluar / Ganti Akun",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }
        }
    }

    if (showAboutDialog) {
        Dialog(onDismissRequest = { showAboutDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(12.dp, shape = RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tentang PetCare Club",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E244B),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "PetCare Club adalah aplikasi asisten kesehatan dan reward member untuk hewan peliharaan Anda.",
                        fontSize = 13.sp,
                        color = Color(0xFF6A729A),
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Versi 1.0.0",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5F69C9)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { showAboutDialog = false },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F69C9))
                    ) {
                        Text(
                            text = "Tutup",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
