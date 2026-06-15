package com.example.petcareclub.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.petcareclub.data.local.dao.MedicalRecordDao
import com.example.petcareclub.data.local.dao.PetDao
import com.example.petcareclub.data.local.dao.UserDao
import com.example.petcareclub.data.local.dao.VoucherDao
import com.example.petcareclub.data.local.entity.MedicalRecord
import com.example.petcareclub.data.local.entity.Pet
import com.example.petcareclub.data.local.entity.User
import com.example.petcareclub.data.local.entity.Voucher

@Database(entities = [User::class, Pet::class, MedicalRecord::class, Voucher::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun petDao(): PetDao
    abstract fun medicalRecordDao(): MedicalRecordDao
    abstract fun voucherDao(): VoucherDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "petcare_club_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
