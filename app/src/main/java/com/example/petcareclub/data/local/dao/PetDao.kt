package com.example.petcareclub.data.local.dao

import androidx.room.*
import com.example.petcareclub.data.local.entity.Pet
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPet(pet: Pet): Long

    @Query("SELECT * FROM pets WHERE userId = :userId")
    fun getPetsForUser(userId: Int): Flow<List<Pet>>

    @Query("SELECT * FROM pets WHERE id = :id")
    fun getPetById(id: Int): Flow<Pet?>

    @Delete
    suspend fun deletePet(pet: Pet)
}
