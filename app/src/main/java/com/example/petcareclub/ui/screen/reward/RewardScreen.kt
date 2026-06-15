package com.example.petcareclub.ui.screen.reward

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petcareclub.R
import com.example.petcareclub.ui.components.PetCareBottomBar
import com.example.petcareclub.ui.navigation.Screen
import com.example.petcareclub.ui.viewmodel.PetCareViewModel
import com.example.petcareclub.ui.viewmodel.RewardItem

@Composable
fun SmallStarCoin(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(18.dp)
            .background(Color(0xFFFFA726), shape = CircleShape)
            .border(0.8.dp, Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardScreen(
    navController: NavController,
    viewModel: PetCareViewModel
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val rewards = viewModel.rewards
    var selectedRewardForRedeem by remember { mutableStateOf<RewardItem?>(null) }

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
            topBar = {
                CenterAlignedTopAppBar(
                    title = { 
                        Text(
                            "Reward", 
                            fontWeight = FontWeight.Bold, 
                            fontSize = 18.sp,
                            color = Color(0xFF1E244B)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack, 
                                contentDescription = "Kembali", 
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
                    currentRoute = Screen.Reward.route
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                // Points Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(vertical = 4.dp)
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
                            .padding(start = 24.dp, end = 12.dp, top = 20.dp, bottom = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Left Info
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Poin Anda",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${currentUser?.points ?: 0}",
                                    color = Color.White,
                                    fontSize = 38.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(8.dp))
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
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Yuk tukarkan poinmu 🎁",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Right 3D Gift Box illustration (Canvas-based)
                        VectorGiftBox(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(110.dp)
                                .padding(end = 8.dp)
                        )
                    }
                }

                if (currentUser == null) {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.08f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "🔒 Mode Tamu: Silakan daftar sebagai Member di menu Profil terlebih dahulu untuk mengaktifkan & menukarkan poin reward.",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(12.dp),
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // List for Rewards
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Text(
                            text = "Daftar Reward",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E244B),
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                    
                    items(rewards) { reward ->
                        RewardItemCard(
                            reward = reward,
                            userPoints = currentUser?.points ?: 0,
                            isGuest = currentUser == null,
                            onRedeem = { selectedRewardForRedeem = reward }
                        )
                    }
                }
            }
        }

        selectedRewardForRedeem?.let { reward ->
            val currentPoints = currentUser?.points ?: 0
            val remainingPoints = currentPoints - reward.pointsCost

            Dialog(onDismissRequest = { selectedRewardForRedeem = null }) {
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
                            text = "Konfirmasi Tukar Poin",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E244B),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .background(Color(0xFFF4F6FA), shape = CircleShape)
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            when (reward.id) {
                                1 -> VectorVaccine(modifier = Modifier.fillMaxSize())
                                2 -> VectorGrooming(modifier = Modifier.fillMaxSize())
                                3 -> VectorCheckUp(modifier = Modifier.fillMaxSize())
                                4 -> VectorPetFood(modifier = Modifier.fillMaxSize())
                                else -> VectorGiftBox(modifier = Modifier.fillMaxSize())
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Apakah Anda yakin ingin menukarkan poin dengan:",
                            fontSize = 13.sp,
                            color = Color(0xFF6A729A),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = reward.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E244B),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FE)),
                            border = BorderStroke(1.dp, Color(0xFFE8EAF6))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Total Poin Saat Ini",
                                        fontSize = 12.sp,
                                        color = Color(0xFF6A729A)
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "$currentPoints",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1E244B)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        SmallStarCoin()
                                    }
                                }
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Biaya Reward",
                                        fontSize = 12.sp,
                                        color = Color(0xFF6A729A)
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "-${reward.pointsCost}",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFFFA726)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        SmallStarCoin()
                                    }
                                }
                                
                                HorizontalDivider(color = Color(0xFFE8EAF6), thickness = 1.dp)
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Poin Setelah Penukaran",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E244B)
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "$remainingPoints",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF5F69C9)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        SmallStarCoin()
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = { selectedRewardForRedeem = null },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(14.dp),
                                border = BorderStroke(1.5.dp, Color(0xFF5F69C9)),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF5F69C9))
                            ) {
                                Text(
                                    text = "Batal",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }

                            Button(
                                onClick = {
                                    viewModel.redeemReward(reward)
                                    selectedRewardForRedeem = null
                                    navController.navigate(Screen.MyVouchers.route)
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F69C9))
                            ) {
                                Text(
                                    text = "Tukar",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RewardItemCard(
    reward: RewardItem,
    userPoints: Int,
    isGuest: Boolean,
    onRedeem: () -> Unit
) {
    val isAffordable = !isGuest && userPoints >= reward.pointsCost
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color(0xFFE8EAF6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circle border around reward icon
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color.White, shape = CircleShape)
                    .border(1.dp, Color(0xFFE8EAF6), CircleShape)
                    .shadow(elevation = 1.dp, shape = CircleShape)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                when (reward.id) {
                    1 -> VectorVaccine(modifier = Modifier.fillMaxSize())
                    2 -> VectorGrooming(modifier = Modifier.fillMaxSize())
                    3 -> VectorCheckUp(modifier = Modifier.fillMaxSize())
                    4 -> VectorPetFood(modifier = Modifier.fillMaxSize())
                    else -> VectorGiftBox(modifier = Modifier.fillMaxSize())
                }
            }

            Spacer(modifier = Modifier.width(18.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = reward.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E244B)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${reward.pointsCost}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFA726)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    SmallStarCoin()
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onRedeem,
                enabled = isAffordable,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF5F69C9),
                    disabledContainerColor = Color(0xFFE8EAF6)
                ),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.height(38.dp)
            ) {
                Text(
                    text = "Tukar",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isAffordable) Color.White else Color(0xFF6A729A).copy(alpha = 0.5f)
                )
            }
        }
    }
}
