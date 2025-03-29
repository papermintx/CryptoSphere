package com.mk.core.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.mk.core.algorithm.AffineCipher
import com.mk.core.algorithm.AutoKeyVigenereCipher
import com.mk.core.algorithm.ExtendedVigenereCipher
import com.mk.core.algorithm.PlayfairCipher
import com.mk.core.algorithm.VigenereCipher
import com.mk.core.domain.model.ResultState
import com.mk.core.domain.repository.CryptoSphereRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CryptoSphereRepositoryImpl(
    private val context: Context
): CryptoSphereRepository {
    override fun encryptFile(inputUri: Uri, key: String): Flow<ResultState<Uri>> {
       return flow {
              Log.d(TAG, "encryptFile: $inputUri")
           emit(ResultState.Loading)
           try {
              val isSuccess = ExtendedVigenereCipher.encryptFileAndOverwrite(context, inputUri, key)
               if (isSuccess){
                   Log.d(TAG, "encryptFile: Success")
                   emit(ResultState.Success(inputUri))
               } else {
                     Log.d(TAG, "encryptFile:  Failed ")
                   emit(ResultState.Error("Failed to encrypt file"))
               }
           } catch (e: Exception) {
                Log.d(TAG, "encryptFile:  Error ")
               emit(ResultState.Error(e.message ?: "Unknown Error"))
           }
       }
    }

    override fun decryptFile(inputUri: Uri, key: String): Flow<ResultState<Uri>> {
        return flow {
            Log.d(TAG, "decryptFile: $inputUri")
            emit(ResultState.Loading)
            try {
                val isSuccess = ExtendedVigenereCipher.decryptFileAndOverwrite(context, inputUri, key)
                 if (isSuccess){
                     Log.d(TAG, "decryptFile: Success")
                        emit(ResultState.Success(inputUri))
                 } else {
                     Log.d(TAG, "decryptFile:  Failed ")
                        emit(ResultState.Error("Failed to decrypt file"))
                 }
            } catch (e: Exception) {
                Log.d(TAG, "decryptFile:  Error ")
                emit(ResultState.Error(e.message ?: "Unknown Error"))
            }
        }
    }

    override fun encryptVigereCipher(
        plaintext: String,
        key: String
    ): Flow<ResultState<String>> {
        return  flow {
            emit(ResultState.Loading)
            try {
                val ciphertext = VigenereCipher.encrypt(plaintext, key)
                emit(ResultState.Success(ciphertext))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message ?: "Unknown Error"))
            }
        }
    }

    override fun decryptVigereCipher(
        ciphertext: String,
        key: String
    ): Flow<ResultState<String>> {
        return  flow {
            Log.d(TAG, "decryptVigereCipher:  ${ciphertext  + key}")
            emit(ResultState.Loading)
            try {
                val plaintext = VigenereCipher.decrypt(ciphertext, key)
                Log.d(TAG, "decryptVigereCipher:  Success $plaintext")
                emit(ResultState.Success(plaintext))
            } catch (e: Exception) {
                Log.d(TAG, "decryptVigereCipher:  $e")
                emit(ResultState.Error(e.message ?: "Unknown Error"))
            }
        }
    }

    override fun encryptAutoKeyVigereCipher(
        plaintext: String,
        key: String
    ): Flow<ResultState<String>> {
        return  flow {
            emit(ResultState.Loading)
            try {
                val ciphertext = AutoKeyVigenereCipher.encrypt(plaintext, key)
                emit(ResultState.Success(ciphertext))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message ?: "Unknown Error"))
            }
        }
    }

    override fun decryptAutoKeyVigereCipher(
        ciphertext: String,
        key: String
    ): Flow<ResultState<String>> {
        return flow {
            emit(ResultState.Loading)
            try {
                val plaintext = AutoKeyVigenereCipher.decrypt(ciphertext, key)
                emit(ResultState.Success(plaintext))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message ?: "Unknown Error"))
            }
        }
    }

    override fun encryptAffineCipher(
        plaintext: String,
        key: Pair<Int, Int>
    ): Flow<ResultState<String>> {
        return flow {
            Log.d(TAG, "encryptAffineCipher: Starting encryption of '$plaintext' with key: ${key.first}, ${key.second}")
            emit(ResultState.Loading)
            try {
                val ciphertext = AffineCipher.encrypt(plaintext, key.first, key.second)
                Log.d(TAG, "encryptAffineCipher: Encryption successful, ciphertext: $ciphertext")
                emit(ResultState.Success(ciphertext))
            } catch (e: Exception) {
                Log.e(TAG, "encryptAffineCipher: Error during encryption", e)
                emit(ResultState.Error(e.message ?: "Unknown Error"))
            }
        }
    }

    override fun decryptAffineCipher(
        ciphertext: String,
        key: Pair<Int, Int>
    ): Flow<ResultState<String>> {
        return flow {
            Log.d(TAG, "decryptAffineCipher: Starting decryption of '$ciphertext' with key: ${key.first}, ${key.second}")
            emit(ResultState.Loading)
            try {
                val plaintext = AffineCipher.decrypt(ciphertext, key.first, key.second)
                Log.d(TAG, "decryptAffineCipher: Decryption successful, plaintext: $plaintext")
                emit(ResultState.Success(plaintext))
            } catch (e: Exception) {
                Log.e(TAG, "decryptAffineCipher: Error during decryption", e)
                emit(ResultState.Error(e.message ?: "Unknown Error"))
            }
        }
    }

    override fun encryptPlayfairCipher(
        plaintext: String,
        key: String
    ): Flow<ResultState<String>> {
        return flow {
            emit(ResultState.Loading)
            try {
                val ciphertext = PlayfairCipher.encrypt(plaintext, key)
                emit(ResultState.Success(ciphertext))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message ?: "Unknown Error"))
            }
        }
    }

    override fun decryptPlayfairCipher(
        ciphertext: String,
        key: String
    ): Flow<ResultState<String>> {
        return flow {
            emit(ResultState.Loading)
            try {
                val plaintext = PlayfairCipher.decrypt(ciphertext, key)
                emit(ResultState.Success(plaintext))
            } catch (e: Exception) {
                emit(ResultState.Error(e.message ?: "Unknown Error"))
            }
        }
    }

    companion object{
        const val  TAG = "CryptoSphereRepositoryImpl"
    }

}