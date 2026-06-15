package com.example.petcareclub.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MedicalRecordWithPet(
    @Embedded val record: MedicalRecord,
    @Relation(
        parentColumn = "petId",
        entityColumn = "id"
    )
    val pet: Pet?
)
