package com.example.petcareclub.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vouchers")
data class Voucher(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val rewardName: String,
    val voucherCode: String,
    val dateRedeemed: String
)
