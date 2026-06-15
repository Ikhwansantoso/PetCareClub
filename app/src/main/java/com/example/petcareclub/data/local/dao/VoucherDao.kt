package com.example.petcareclub.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.petcareclub.data.local.entity.Voucher
import kotlinx.coroutines.flow.Flow

@Dao
interface VoucherDao {
    @Query("SELECT * FROM vouchers WHERE userId = :userId ORDER BY id DESC")
    fun getVouchersForUser(userId: Int): Flow<List<Voucher>>

    @Insert
    suspend fun insertVoucher(voucher: Voucher): Long

    @Delete
    suspend fun deleteVoucher(voucher: Voucher)
}
