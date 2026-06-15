package com.example.petcareclub.ui.screen.reward

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.petcareclub.R
import com.example.petcareclub.data.local.entity.Voucher
import com.example.petcareclub.ui.navigation.Screen
import com.example.petcareclub.ui.viewmodel.PetCareViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyVouchersScreen(
    navController: NavController,
    viewModel: PetCareViewModel
) {
    val userVouchers by viewModel.userVouchers.collectAsState()
    var selectedVoucherForDialog by remember { mutableStateOf<Voucher?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.login_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Light background overlay
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
                        Box(modifier = Modifier.fillMaxWidth().padding(end = 48.dp), contentAlignment = Alignment.Center) {
                            Text("Voucher Saya", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali", tint = MaterialTheme.colorScheme.primary)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            bottomBar = {
                com.example.petcareclub.ui.components.PetCareBottomBar(
                    navController = navController,
                    currentRoute = Screen.Profile.route
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                if (userVouchers.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(24.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                        ) {
                            Column(
                                modifier = Modifier.padding(28.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "🎟️", fontSize = 48.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Belum Ada Voucher",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Tukarkan poin Anda di menu katalog Reward untuk mengklaim voucher diskon/gratis.",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 80.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(userVouchers) { voucher ->
                            VoucherCard(
                                voucher = voucher,
                                onClick = { selectedVoucherForDialog = voucher }
                            )
                        }
                    }
                }
            }
        }
    }

    selectedVoucherForDialog?.let { voucher ->
        Dialog(onDismissRequest = { selectedVoucherForDialog = null }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Voucher Member",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = voucher.rewardName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // QR Code
                    SimulatedQrCode(
                        modifier = Modifier
                            .size(160.dp)
                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(4.dp),
                        code = voucher.voucherCode
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "KODE VOUCHER",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                    Text(
                        text = voucher.voucherCode,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.5.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Tunjukkan QR Code ini ke kasir klinik untuk menggunakan voucher.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            viewModel.useVoucher(voucher)
                            selectedVoucherForDialog = null
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = "Gunakan Voucher (Simulasi)",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = { selectedVoucherForDialog = null },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Tutup",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VoucherCard(
    voucher: Voucher,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = voucher.rewardName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Kupon diklaim pada ${voucher.dateRedeemed}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = voucher.voucherCode,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        letterSpacing = 1.sp
                    )
                }
            }
            Text(text = "🎟️", fontSize = 28.sp)
        }
    }
}

@Composable
fun SimulatedQrCode(
    modifier: Modifier = Modifier,
    code: String
) {
    val seed = remember(code) { code.hashCode().toLong() }
    val random = remember(seed) { java.util.Random(seed) }
    
    val gridSize = 15
    val grid = remember(code) {
        Array(gridSize) { r ->
            BooleanArray(gridSize) { c ->
                val isTlFinder = r < 5 && c < 5
                val isTrFinder = r < 5 && c >= gridSize - 5
                val isBlFinder = r >= gridSize - 5 && c < 5
                
                if (isTlFinder || isTrFinder || isBlFinder) {
                    val localR = if (isTlFinder || isTrFinder) r else r - (gridSize - 5)
                    val localC = if (isTlFinder || isBlFinder) c else c - (gridSize - 5)
                    val isBorder = localR == 0 || localR == 4 || localC == 0 || localC == 4
                    val isCenter = localR == 2 && localC == 2
                    isBorder || isCenter
                } else {
                    random.nextBoolean()
                }
            }
        }
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(Color.White)
            .padding(12.dp)
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val cellWidth = size.width / gridSize
            val cellHeight = size.height / gridSize
            for (r in 0 until gridSize) {
                for (c in 0 until gridSize) {
                    if (grid[r][c]) {
                        drawRect(
                            color = Color(0xFF1E293B),
                            topLeft = androidx.compose.ui.geometry.Offset(c * cellWidth, r * cellHeight),
                            size = androidx.compose.ui.geometry.Size(cellWidth + 0.5f, cellHeight + 0.5f)
                        )
                    }
                }
            }
        }
    }
}

