package com.mk.core.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "encrypt_table")
data class EncryptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cipherName: String,
    val key: String?,
    val algorithm: String?,
    val plaintext: String?,
    val ciphertext: String?,
    val filePath: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
