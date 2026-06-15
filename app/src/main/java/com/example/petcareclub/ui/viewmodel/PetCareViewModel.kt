package com.example.petcareclub.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.petcareclub.data.local.entity.MedicalRecord
import com.example.petcareclub.data.local.entity.Pet
import com.example.petcareclub.data.local.entity.User
import com.example.petcareclub.data.local.entity.Voucher
import com.example.petcareclub.data.repository.PetCareRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class RewardItem(
    val id: Int,
    val name: String,
    val description: String,
    val pointsCost: Int
)

class PetCareViewModel(private val repository: PetCareRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            val users = repository.allUsers.first()
            if (users.isEmpty()) {
                val userId = repository.insertUser(User(name = "Ikhwan", email = "ikhwan@email.com", points = 120))
                repository.insertPet(Pet(userId = userId.toInt(), name = "Mimi", type = "Kucing Persia", age = "2 Tahun 6 Bulan", weight = "4.2", photoPath = "pet_mimi"))
                repository.insertPet(Pet(userId = userId.toInt(), name = "Bobby", type = "Anjing Golden Retriever", age = "3 Tahun", weight = "15.0", photoPath = "pet_bobby"))
                repository.insertPet(Pet(userId = userId.toInt(), name = "Luna", type = "Kelinci", age = "1 Tahun", weight = "1.5", photoPath = "pet_luna"))
            }

            // Delete user named "Keisha" if they exist
            val allCurrentUsers = repository.allUsers.first()
            val keisha = allCurrentUsers.find { it.name.equals("Keisha", ignoreCase = true) }
            if (keisha != null) {
                try {
                    // Delete Keisha's medical records
                    val records = repository.getRecordsWithPetForUser(keisha.id).first()
                    records.forEach { recordWithPet ->
                        repository.deleteMedicalRecord(recordWithPet.record)
                    }
                    // Delete Keisha's pets
                    val pets = repository.getPetsForUser(keisha.id).first()
                    pets.forEach { repository.deletePet(it) }
                    // Delete Keisha's vouchers
                    val vouchers = repository.getVouchersForUser(keisha.id).first()
                    vouchers.forEach { repository.deleteVoucher(it) }
                    // Delete Keisha user
                    repository.deleteUser(keisha)
                    
                    if (_currentUserId.value == keisha.id) {
                        _currentUserId.value = null
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


        }
    }


    // Reward list (hardcoded static rewards)
    val rewards = listOf(
        RewardItem(1, "Gratis Vaksin Rabies", "Dapatkan vaksinasi rabies gratis untuk hewan kesayangan Anda.", 50),
        RewardItem(2, "Diskon Grooming 20%", "Potongan harga untuk mandi dan potong rambut hewan.", 30),
        RewardItem(3, "Gratis Check-up", "Konsultasi pemeriksaan kesehatan lengkap dengan dokter hewan.", 80),
        RewardItem(4, "Makanan Premium (1 Kg)", "Satu kemasan pakan premium bernutrisi tinggi gratis.", 40)
    )

    val allUsers: StateFlow<List<User>> = repository.allUsers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _currentUserId = MutableStateFlow<Int?>(null)
    val currentUserId: StateFlow<Int?> = _currentUserId.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val currentUser: StateFlow<User?> = _currentUserId
        .flatMapLatest { id ->
            if (id != null && id != -1) repository.getUserById(id) else flowOf(null)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val userPets: StateFlow<List<Pet>> = _currentUserId
        .flatMapLatest { id ->
            if (id != null) repository.getPetsForUser(id) else flowOf(emptyList())
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val userVouchers: StateFlow<List<Voucher>> = _currentUserId
        .flatMapLatest { id ->
            if (id != null && id != -1) repository.getVouchersForUser(id) else flowOf(emptyList())
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedPetId = MutableStateFlow<Int?>(null)
    val selectedPetId: StateFlow<Int?> = _selectedPetId.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedPet: StateFlow<Pet?> = _selectedPetId
        .flatMapLatest { id ->
            if (id != null) repository.getPetById(id) else flowOf(null)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedPetRecords: StateFlow<List<MedicalRecord>> = _selectedPetId
        .flatMapLatest { id ->
            if (id != null) repository.getRecordsForPet(id) else flowOf(emptyList())
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val userMedicalRecords: StateFlow<List<com.example.petcareclub.data.local.entity.MedicalRecordWithPet>> = _currentUserId
        .flatMapLatest { userId ->
            if (userId != null) repository.getRecordsWithPetForUser(userId) else flowOf(emptyList())
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // A status message state for showing snackbar/toasts in UI
    private val _statusMessage = MutableSharedFlow<String>()
    val statusMessage = _statusMessage.asSharedFlow()

    fun loginUser(userId: Int) {
        _currentUserId.value = userId
        _selectedPetId.value = null
        viewModelScope.launch {
            _statusMessage.emit("Berhasil masuk!")
        }
    }

    fun loginAsGuest() {
        _currentUserId.value = -1
        _selectedPetId.value = null
        viewModelScope.launch {
            _statusMessage.emit("Masuk sebagai Tamu")
        }
    }

    fun loginWithEmail(email: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            if (user != null) {
                _currentUserId.value = user.id
                _selectedPetId.value = null
                _statusMessage.emit("Selamat datang kembali, ${user.name}!")
                onComplete(true)
            } else {
                _statusMessage.emit("Email tidak ditemukan!")
                onComplete(false)
            }
        }
    }

    fun registerUser(name: String, email: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (name.isBlank() || email.isBlank()) {
                _statusMessage.emit("Nama dan email tidak boleh kosong!")
                onComplete(false)
                return@launch
            }

            // Standard email validation (e.g. contains @ and ends with a domain name)
            val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
            if (!email.trim().matches(emailRegex)) {
                _statusMessage.emit("Format email tidak valid! Gunakan email standar (contoh: nama@gmail.com).")
                onComplete(false)
                return@launch
            }

            val existing = repository.getUserByEmail(email.trim())
            if (existing != null) {
                _statusMessage.emit("Email sudah terdaftar! Masuk sebagai akun tersebut.")
                _currentUserId.value = existing.id
                onComplete(true)
                return@launch
            }
            val newUser = User(name = name, email = email, points = 0)
            val newId = repository.insertUser(newUser)
            
            // Migrate guest pets (userId = -1) to new user ID
            try {
                val guestPets = repository.getPetsForUser(-1).first()
                guestPets.forEach { pet ->
                    repository.insertPet(pet.copy(userId = newId.toInt()))
                    repository.deletePet(pet)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            
            _currentUserId.value = newId.toInt()
            _statusMessage.emit("Pendaftaran member berhasil!")
            onComplete(true)
        }
    }

    fun addPet(name: String, type: String, age: String, weight: String, photoPath: String?, onComplete: () -> Unit) {
        val userId = _currentUserId.value
        if (userId == null) {
            viewModelScope.launch { _statusMessage.emit("Error: Sesi pengguna habis!") }
            return
        }
        if (name.isBlank() || type.isBlank()) {
            viewModelScope.launch { _statusMessage.emit("Nama dan jenis hewan harus diisi!") }
            return
        }
        viewModelScope.launch {
            repository.insertPet(Pet(userId = userId, name = name, type = type, age = age, weight = weight, photoPath = photoPath))
            _statusMessage.emit("Berhasil menambahkan hewan peliharaan!")
            onComplete()
        }
    }

    fun selectPet(petId: Int) {
        _selectedPetId.value = petId
    }

    fun addMedicalRecord(treatment: String, date: String, onComplete: () -> Unit) {
        val petId = _selectedPetId.value
        val userId = _currentUserId.value
        if (petId == null || userId == null) {
            viewModelScope.launch { _statusMessage.emit("Error: Hewan atau sesi tidak valid!") }
            return
        }
        if (treatment.isBlank() || date.isBlank()) {
            viewModelScope.launch { _statusMessage.emit("Perawatan dan tanggal harus diisi!") }
            return
        }
        viewModelScope.launch {
            repository.insertMedicalRecord(
                MedicalRecord(petId = petId, treatment = treatment, date = date),
                userId
            )
            _statusMessage.emit("Riwayat dicatat! Anda mendapatkan +10 Poin 🎁")
            onComplete()
        }
    }

    fun redeemReward(reward: RewardItem) {
        val user = currentUser.value
        if (user == null) {
            viewModelScope.launch { _statusMessage.emit("Error: Sesi pengguna tidak ditemukan!") }
            return
        }
        if (user.points < reward.pointsCost) {
            viewModelScope.launch { _statusMessage.emit("Poin tidak cukup untuk menukarkan reward!") }
            return
        }
        viewModelScope.launch {
            val updatedUser = user.copy(points = user.points - reward.pointsCost)
            repository.updateUser(updatedUser)
            
            // Create active claim Voucher
            val claimCode = "PC-VCR-${(1000..9999).random()}"
            val sdf = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
            val today = sdf.format(Date())
            repository.insertVoucher(
                Voucher(
                    userId = user.id,
                    rewardName = reward.name,
                    voucherCode = claimCode,
                    dateRedeemed = today
                )
            )
            
            _statusMessage.emit("Berhasil menukarkan '${reward.name}'! Poin berkurang ${reward.pointsCost}. Kupon voucher aktif disimpan.")
        }
    }

    fun useVoucher(voucher: Voucher) {
        viewModelScope.launch {
            repository.deleteVoucher(voucher)
            _statusMessage.emit("Voucher '${voucher.rewardName}' berhasil digunakan oleh kasir! Layanan/diskon Anda telah aktif. 🎟️")
        }
    }

    fun updateUserProfilePhoto(photoPath: String) {
        val user = currentUser.value ?: return
        viewModelScope.launch {
            val updatedUser = user.copy(photoPath = photoPath)
            repository.updateUser(updatedUser)
            _statusMessage.emit("Foto profil berhasil diperbarui! 📸")
        }
    }



    fun logout() {
        _currentUserId.value = null
        _selectedPetId.value = null
        viewModelScope.launch {
            _statusMessage.emit("Berhasil keluar.")
        }
    }
}

class PetCareViewModelFactory(private val repository: PetCareRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PetCareViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PetCareViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
