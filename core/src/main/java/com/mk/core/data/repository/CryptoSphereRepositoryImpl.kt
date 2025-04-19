package com.mk.core.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.sqlite.db.SupportSQLiteProgram
import androidx.sqlite.db.SupportSQLiteQuery
import com.mk.core.algorithm.AffineCipher
import com.mk.core.algorithm.AutoKeyVigenereCipher
import com.mk.core.algorithm.ExtendedVigenereCipher
import com.mk.core.algorithm.PlayfairCipher
import com.mk.core.algorithm.VigenereCipher
import com.mk.core.data.room.dao.EncryptDao
import com.mk.core.data.room.model.EncryptEntity
import com.mk.core.domain.model.ResultState
import com.mk.core.domain.repository.CryptoSphereRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CryptoSphereRepositoryImpl(
    private val context: Context,
    private val encryptDao: EncryptDao
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

    override fun encryptVigenereCipher(
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

    override fun decryptVigenereCipher(
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

    override fun encryptAutoKeyVigenereCipher(
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

    override fun decryptAutoKeyVigenereCipher(
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

    override suspend fun insertHistory(entity: EncryptEntity) {
        try {
            encryptDao.insertEncrypt(entity)
        } catch (e: Exception){
            Log.e(TAG, "insertHistory: $e" )
        }
    }


    override fun getHistoryByCipher(
        cipherName: String,
        sortedDesc: Boolean
    ): Flow<List<EncryptEntity>> {
        val query = object : SupportSQLiteQuery {
            override val argCount: Int
                get() =1
            override val sql: String
                get() = "SELECT * FROM encrypt_table WHERE cipherName = ? ORDER BY createdAt ${if (sortedDesc) "DESC" else "ASC"}"

            override fun bindTo(statement: SupportSQLiteProgram) {
                statement.bindString(1, cipherName)
            }
        }

        return encryptDao.getSortedHistory(query)
    }

    override suspend fun deleteHistoryById(id: Int) {
        try {
            encryptDao.deleteById(id)
        } catch (e: Exception){
            Log.e(TAG, "deleteHistoryById: $e" )
        }
    }

    override suspend fun deleteHistoryByCipher(cipherName: String) {
        try {
            encryptDao.deleteByCipher(cipherName)
        } catch (e: Exception){
            Log.e(TAG, "deleteHistoryByCipher: $e" )
        }
    }

    companion object{
        const val  TAG = "CryptoSphereRepositoryImpl"
    }

}