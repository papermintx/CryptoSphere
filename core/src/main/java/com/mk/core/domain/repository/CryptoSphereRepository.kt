package com.mk.core.domain.repository

import android.net.Uri
import com.mk.core.domain.model.ResultState
import kotlinx.coroutines.flow.Flow

interface CryptoSphereRepository {
    fun encryptFile(inputUri: Uri, key: String): Flow<ResultState<Uri>>
    fun decryptFile(inputUri: Uri, key: String): Flow<ResultState<Uri>>

    fun  encryptVigereCipher(plaintext: String, key: String): Flow<ResultState<String>>
    fun  decryptVigereCipher(ciphertext: String, key: String): Flow<ResultState<String>>

    fun encryptAutoKeyVigereCipher(plaintext: String, key: String): Flow<ResultState<String>>
    fun decryptAutoKeyVigereCipher(ciphertext: String, key: String): Flow<ResultState<String>>

    fun encryptAffineCipher(plaintext: String, key: Pair<Int, Int>): Flow<ResultState<String>>
    fun decryptAffineCipher(ciphertext: String, key: Pair<Int, Int>): Flow<ResultState<String>>
}
