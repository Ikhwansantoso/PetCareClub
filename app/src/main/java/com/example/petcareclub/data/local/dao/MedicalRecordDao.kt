package com.example.petcareclub.data.local.dao

import androidx.room.*
import com.example.petcareclub.data.local.entity.MedicalRecord
import com.example.petcareclub.data.local.entity.MedicalRecordWithPet
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicalRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: MedicalRecord): Long

    @Query("SELECT * FROM medical_records WHERE petId = :petId ORDER BY date DESC")
    fun getRecordsForPet(petId: Int): Flow<List<MedicalRecord>>

    @Transaction
    @Query("""
        SELECT * FROM medical_records 
        WHERE petId IN (SELECT id FROM pets WHERE userId = :userId) 
        ORDER BY date DESC
    """)
    fun getRecordsWithPetForUser(userId: Int): Flow<List<MedicalRecordWithPet>>

    @Delete
    suspend fun deleteRecord(record: MedicalRecord)
}
