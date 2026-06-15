package com.example.petcareclub.data.repository

import com.example.petcareclub.data.local.dao.MedicalRecordDao
import com.example.petcareclub.data.local.dao.PetDao
import com.example.petcareclub.data.local.dao.UserDao
import com.example.petcareclub.data.local.dao.VoucherDao
import com.example.petcareclub.data.local.entity.MedicalRecord
import com.example.petcareclub.data.local.entity.MedicalRecordWithPet
import com.example.petcareclub.data.local.entity.Pet
import com.example.petcareclub.data.local.entity.User
import com.example.petcareclub.data.local.entity.Voucher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class PetCareRepository(
    private val userDao: UserDao,
    private val petDao: PetDao,
    private val medicalRecordDao: MedicalRecordDao,
    private val voucherDao: VoucherDao
) {
    val allUsers: Flow<List<User>> = userDao.getAllUsers()

    fun getUserById(id: Int): Flow<User?> = userDao.getUserById(id)

    suspend fun getUserByEmail(email: String): User? = userDao.getUserByEmail(email)

    suspend fun insertUser(user: User): Long = userDao.insertUser(user)

    suspend fun updateUser(user: User) = userDao.updateUser(user)

    suspend fun deleteUser(user: User) = userDao.deleteUser(user)

    fun getPetsForUser(userId: Int): Flow<List<Pet>> = petDao.getPetsForUser(userId)

    fun getPetById(id: Int): Flow<Pet?> = petDao.getPetById(id)

    suspend fun insertPet(pet: Pet): Long = petDao.insertPet(pet)

    suspend fun deletePet(pet: Pet) = petDao.deletePet(pet)

    fun getRecordsForPet(petId: Int): Flow<List<MedicalRecord>> = medicalRecordDao.getRecordsForPet(petId)

    fun getRecordsWithPetForUser(userId: Int): Flow<List<MedicalRecordWithPet>> =
        medicalRecordDao.getRecordsWithPetForUser(userId)

    suspend fun insertMedicalRecord(record: MedicalRecord, userId: Int): Long {
        val recordId = medicalRecordDao.insertRecord(record)
        // Add 10 points to the user
        try {
            val user = userDao.getUserById(userId).first()
            if (user != null) {
                userDao.updateUser(user.copy(points = user.points + 10))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return recordId
    }

    suspend fun deleteMedicalRecord(record: MedicalRecord) = medicalRecordDao.deleteRecord(record)

    fun getVouchersForUser(userId: Int): Flow<List<Voucher>> = voucherDao.getVouchersForUser(userId)

    suspend fun insertVoucher(voucher: Voucher): Long = voucherDao.insertVoucher(voucher)

    suspend fun deleteVoucher(voucher: Voucher) = voucherDao.deleteVoucher(voucher)
}
