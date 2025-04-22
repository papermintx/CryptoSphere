package com.mk.cryptosphere.ui.presentation.hill_cipher

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.core.domain.model.ResultState
import com.mk.core.domain.usecase.DecryptHillCipherUseCase
import com.mk.core.domain.usecase.EncryptHillCipherUseCase
import com.mk.cryptosphere.common.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HillCipherViewModel @Inject constructor(
    private val encryptHillCipherUseCase: EncryptHillCipherUseCase,
    private val decryptHillCipherUseCase: DecryptHillCipherUseCase,
): ViewModel() {


    private val _state = MutableStateFlow<ResultState<ResultData>>(ResultState.Idle)
    val state = _state.asStateFlow()

    val plaintextData = MutableStateFlow<String>("")
    val keyData = MutableStateFlow<ArrayList<Array<Int>>?>(null)


    fun encrypt(plaintext: String, key: ArrayList<Array<Int>>) {
        viewModelScope.launch {
            // Update LiveData untuk plaintext dan key
            plaintextData.value = plaintext
            keyData.value = key

            Log.d("HillCipher", "Starting encryption with plaintext: $plaintext")
            Log.d("HillCipher", "Key matrix: $key")

            try {
                // Konversi key menjadi bentuk array untuk pemrosesan
                val array2 = Array(key.size) { IntArray(key[0].size) }
                for (i in key.indices) {
                    array2[i] = key[i].toIntArray()  // Mengonversi Array<Int> ke IntArray
                }

                Log.d("HillCipher", "Converted key to array2: ${array2.contentDeepToString()}")

                encryptHillCipherUseCase(plaintext, array2).collect { result ->
                    val resultState = when (result) {
                        is ResultState.Success -> {
                            Log.d("HillCipher", "Encryption successful, ciphertext: ${result.data}")
                            ResultState.Success(
                                ResultData(
                                    plaintext = plaintextData.value,
                                    ciphertext = result.data,
                                    isDecrypt = false,
                                    arrayKeyHill = keyData.value,
                                )
                            )
                        }
                        is ResultState.Error -> {
                            Log.e("HillCipher", "Encryption failed: ${result.message}")
                            ResultState.Error(result.message)
                        }
                        is ResultState.Loading -> {
                            Log.d("HillCipher", "Encryption is in progress...")
                            ResultState.Loading
                        }
                        else -> {
                            Log.d("HillCipher", "Idle state")
                            ResultState.Idle
                        }
                    }
                    _state.value = resultState
                }

            } catch (e: Exception) {
                Log.e("HillCipher", "Encryption failed: ${e.message}")
                _state.value = ResultState.Error("Encryption failed: ${e.message}")
            }
        }
    }



    fun decrypt(ciphertext: String, key: ArrayList<Array<Int>>) = viewModelScope.launch {
        // Update LiveData untuk key
        keyData.value = key

        Log.d("HillCipher", "Starting decryption with ciphertext: $ciphertext")
        Log.d("HillCipher", "Key matrix: ${key.joinToString(", ") { it.joinToString(", ") }}")  // Debugging key matrix

        try {
            // Konversi key menjadi bentuk array untuk pemrosesan
            val array2 = Array(key.size) { IntArray(key[0].size) }
            for (i in key.indices) {
                array2[i] = key[i].toIntArray()  // Mengonversi Array<Int> ke IntArray
            }

            Log.d("HillCipher", "Converted key to array2: ${array2.contentDeepToString()}")  // Debugging array2

            decryptHillCipherUseCase(ciphertext, array2).collect { result ->
                val resultState = when (result) {
                    is ResultState.Success -> {
                        Log.d("HillCipher", "Decryption successful, plaintext: ${result.data}")
                        ResultState.Success(
                            ResultData(
                                plaintext = result.data,
                                ciphertext = ciphertext,
                                isDecrypt = true,
                                arrayKeyHill = keyData.value,
                            )
                        )
                    }
                    is ResultState.Error -> {
                        Log.e("HillCipher", "Decryption failed: ${result.message}")
                        ResultState.Error(result.message)
                    }
                    is ResultState.Loading -> {
                        Log.d("HillCipher", "Decryption is in progress...")
                        ResultState.Loading
                    }
                    else -> {
                        Log.d("HillCipher", "Idle state")
                        ResultState.Idle
                    }
                }
                _state.value = resultState
            }
        } catch (e: Exception) {
            Log.e("HillCipher", "Decryption failed: ${e.message}")
            _state.value = ResultState.Error("Decryption failed: ${e.message}")
        }
    }


    fun resetState() {
        _state.value = ResultState.Idle
    }

}