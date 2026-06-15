package com.example.petcareclub.ui.screen.pet

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petcareclub.R
import com.example.petcareclub.ui.viewmodel.PetCareViewModel

// Custom dashed border modifier for the photo picker
fun Modifier.dashedBorder(strokeWidth: Dp, color: Color, cornerRadius: Dp) = this.drawBehind {
    val strokeWidthPx = strokeWidth.toPx()
    val cornerRadiusPx = cornerRadius.toPx()
    val stroke = Stroke(
        width = strokeWidthPx,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f), 0f)
    )
    drawRoundRect(
        color = color,
        style = stroke,
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(cornerRadiusPx, cornerRadiusPx)
    )
}

// Custom camera vector icon
val CameraIcon: ImageVector
    get() = ImageVector.Builder(
        name = "CameraIcon",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(Color(0xFF5F69C9)),
            strokeLineWidth = 2f
        ) {
            moveTo(4f, 8f)
            lineTo(8f, 8f)
            lineTo(9.5f, 5.5f)
            lineTo(14.5f, 5.5f)
            lineTo(16f, 8f)
            lineTo(20f, 8f)
            curveTo(21.1f, 8f, 22f, 8.9f, 22f, 10f)
            lineTo(22f, 18f)
            curveTo(22f, 19.1f, 21.1f, 20f, 20f, 20f)
            lineTo(4f, 20f)
            curveTo(2.9f, 20f, 2f, 19.1f, 2f, 18f)
            lineTo(2f, 10f)
            curveTo(2f, 8.9f, 2.9f, 8f, 4f, 8f)
            close()
        }
        path(
            stroke = SolidColor(Color(0xFF5F69C9)),
            strokeLineWidth = 2f
        ) {
            moveTo(12f, 11f)
            curveTo(13.66f, 11f, 15f, 12.34f, 15f, 14f)
            curveTo(15f, 15.66f, 13.66f, 17f, 12f, 17f)
            curveTo(10.34f, 17f, 9f, 15.66f, 9f, 14f)
            curveTo(9f, 12.34f, 10.34f, 11f, 12f, 11f)
            close()
        }
    }.build()

@Composable
fun PetTypeChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(42.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = if (selected) Color(0xFF5F69C9) else Color.White
            )
            .border(
                width = 1.dp,
                color = if (selected) Color.Transparent else Color(0xFFE8EAF6),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) Color.White else Color(0xFF6A729A),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(
    navController: NavController,
    viewModel: PetCareViewModel
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    
    // Split age into Years and Months
    var yearsString by remember { mutableStateOf("") }
    var monthsString by remember { mutableStateOf("") }
    var weightString by remember { mutableStateOf("") }
    var photoPath by remember { mutableStateOf<String?>(null) }
    
    // Preset pet types with emoji matching mockup list
    var selectedType by remember { mutableStateOf("Kucing") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val file = java.io.File(context.filesDir, "pet_photo_${System.currentTimeMillis()}.jpg")
                val outputStream = java.io.FileOutputStream(file)
                inputStream?.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                photoPath = file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Gagal memuat gambar!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    val photoBitmap = remember(photoPath) {
        if (!photoPath.isNullOrEmpty()) {
            val file = java.io.File(photoPath!!)
            if (file.exists()) {
                android.graphics.BitmapFactory.decodeFile(file.absolutePath)
            } else {
                null
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
                .background(Color(0xFFF4F6FA).copy(alpha = 0.92f))
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { 
                        Text(
                            "Tambah Hewan", 
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color(0xFF1E244B)
                        ) 
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack, 
                                contentDescription = "Kembali", 
                                tint = Color(0xFF1E244B)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Informasi Hewan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E244B),
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // Label: Nama Hewan
                Text(
                    text = "Nama Hewan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E244B),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Masukkan nama hewan", color = Color(0xFF6A729A).copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF5F69C9),
                        unfocusedBorderColor = Color(0xFFE8EAF6),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color(0xFF1E244B),
                        unfocusedTextColor = Color(0xFF1E244B)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Label: Jenis Hewan
                Text(
                    text = "Jenis Hewan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E244B),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Grid layout for 5 preset chips
                Row(modifier = Modifier.fillMaxWidth()) {
                    PetTypeChip(text = "🐱 Kucing", selected = selectedType == "Kucing", onClick = { selectedType = "Kucing" }, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    PetTypeChip(text = "🐶 Anjing", selected = selectedType == "Anjing", onClick = { selectedType = "Anjing" }, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    PetTypeChip(text = "🐰 Kelinci", selected = selectedType == "Kelinci", onClick = { selectedType = "Kelinci" }, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    PetTypeChip(text = "🐹 Hamster", selected = selectedType == "Hamster", onClick = { selectedType = "Hamster" }, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    PetTypeChip(text = "🐦 Burung", selected = selectedType == "Burung", onClick = { selectedType = "Burung" }, modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(8.dp))
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Label: Umur Hewan
                Text(
                    text = "Umur Hewan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E244B),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = yearsString,
                        onValueChange = { yearsString = it },
                        placeholder = { Text("0", color = Color(0xFF6A729A).copy(alpha = 0.5f)) },
                        suffix = { 
                            Text(
                                text = "Tahun", 
                                color = Color(0xFF6A729A), 
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            ) 
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF5F69C9),
                            unfocusedBorderColor = Color(0xFFE8EAF6),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = Color(0xFF1E244B),
                            unfocusedTextColor = Color(0xFF1E244B)
                        ),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = monthsString,
                        onValueChange = { monthsString = it },
                        placeholder = { Text("0", color = Color(0xFF6A729A).copy(alpha = 0.5f)) },
                        suffix = { 
                            Text(
                                text = "Bulan", 
                                color = Color(0xFF6A729A), 
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            ) 
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF5F69C9),
                            unfocusedBorderColor = Color(0xFFE8EAF6),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = Color(0xFF1E244B),
                            unfocusedTextColor = Color(0xFF1E244B)
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Label: Berat (Opsional)
                Text(
                    text = "Berat (Opsional)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E244B),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = weightString,
                    onValueChange = { weightString = it },
                    placeholder = { Text("Masukkan berat hewan (kg)", color = Color(0xFF6A729A).copy(alpha = 0.5f)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF5F69C9),
                        unfocusedBorderColor = Color(0xFFE8EAF6),
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color(0xFF1E244B),
                        unfocusedTextColor = Color(0xFF1E244B)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Label: Foto Hewan
                Text(
                    text = "Foto Hewan",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E244B),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (photoBitmap != null) {
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .border(1.dp, Color(0xFFE8EAF6), RoundedCornerShape(14.dp))
                        ) {
                            Image(
                                bitmap = photoBitmap.asImageBitmap(),
                                contentDescription = "Preview",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .dashedBorder(1.5.dp, Color(0xFF5F69C9).copy(alpha = 0.5f), 14.dp)
                            .background(Color(0xFF5F69C9).copy(alpha = 0.05f), RoundedCornerShape(14.dp))
                            .clickable { imagePickerLauncher.launch("image/*") }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = CameraIcon,
                                contentDescription = null,
                                tint = Color(0xFF5F69C9),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Pilih Foto",
                                color = Color(0xFF5F69C9),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                Button(
                    onClick = {
                        val years = yearsString.toIntOrNull() ?: 0
                        val months = monthsString.toIntOrNull() ?: 0
                        
                        val ageText = when {
                            years > 0 && months > 0 -> "$years Tahun $months Bulan"
                            years > 0 -> "$years Tahun"
                            months > 0 -> "$months Bulan"
                            else -> "Baru Lahir"
                        }

                        if (name.isBlank()) {
                            Toast.makeText(context, "Nama hewan harus diisi!", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val weightText = if (weightString.isNotBlank()) "$weightString kg" else "-"

                        viewModel.addPet(name, selectedType, ageText, weightText, photoPath) {
                            navController.navigateUp()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5F69C9))
                ) {
                    Text(
                        text = "Simpan Hewan",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
