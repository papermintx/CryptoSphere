package com.mk.core.domain.repository

import android.net.Uri
import com.mk.core.data.room.model.EncryptEntity
import com.mk.core.domain.model.ResultState
import kotlinx.coroutines.flow.Flow

interface CryptoSphereRepository {

    // ==== Enkripsi / Dekripsi File ====
    fun encryptFile(inputUri: Uri, key: String): Flow<ResultState<Uri>>
    fun decryptFile(inputUri: Uri, key: String): Flow<ResultState<Uri>>

    // ==== Cipher: Vigenere ====
    fun encryptVigenereCipher(plaintext: String, key: String): Flow<ResultState<String>>
    fun decryptVigenereCipher(ciphertext: String, key: String): Flow<ResultState<String>>

    // ==== Cipher: Auto-key Vigenere ====
    fun encryptAutoKeyVigenereCipher(plaintext: String, key: String): Flow<ResultState<String>>
    fun decryptAutoKeyVigenereCipher(ciphertext: String, key: String): Flow<ResultState<String>>

    // ==== Cipher: Affine ====
    fun encryptAffineCipher(plaintext: String, key: Pair<Int, Int>): Flow<ResultState<String>>
    fun decryptAffineCipher(ciphertext: String, key: Pair<Int, Int>): Flow<ResultState<String>>

    // ==== Cipher: Playfair ====
    fun encryptPlayfairCipher(plaintext: String, key: String): Flow<ResultState<String>>
    fun decryptPlayfairCipher(ciphertext: String, key: String): Flow<ResultState<String>>

    // ==== RIWAYAT / HISTORY ====

    // Simpan riwayat enkripsi/dekripsi
    suspend fun insertHistory(entity: EncryptEntity)

    // Ambil riwayat berdasarkan cipherName, bisa disortir ASC/DESC
    fun getHistoryByCipher(cipherName: String, sortedDesc: Boolean = true): Flow<List<EncryptEntity>>

    // Hapus riwayat berdasarkan ID
    suspend fun deleteHistoryById(id: Int)

    // Hapus semua riwayat untuk algoritma tertentu
    suspend fun deleteHistoryByCipher(cipherName: String)
}

