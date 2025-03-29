package com.mk.cryptosphere.ui.presentation.vigenere_cipher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.core.domain.model.ResultState
import com.mk.core.domain.usecase.DecryptVigenereCipherUseCase
import com.mk.core.domain.usecase.EncryptVigenereCipherUseCase
import com.mk.cryptosphere.common.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VigenereCipherViewModel @Inject constructor(
    private val encryptVigenereCipherUseCase: EncryptVigenereCipherUseCase,
    private val decryptVigenereCipherUseCase: DecryptVigenereCipherUseCase
): ViewModel() {

    private val _state = MutableStateFlow<ResultState<ResultData>>(ResultState.Idle)
    val state = _state.asStateFlow()

    val plaintextData = MutableStateFlow<String>("")
    val keyData = MutableStateFlow<String>("")

    fun encrypt(plaintext: String, key: String) = viewModelScope.launch {
        plaintextData.value = plaintext
        keyData.value = key
        encryptVigenereCipherUseCase(plaintext, key).collect {
            val result = when(it) {
                is ResultState.Success -> {
                    ResultState.Success(
                        ResultData(
                            plaintext = plaintextData.value,
                            key = keyData.value,
                            ciphertext = it.data,
                            isDecrypt = false
                        )
                    )
                }
                is ResultState.Error -> ResultState.Error(it.message)
                is ResultState.Loading -> ResultState.Loading
                else -> ResultState.Idle
            }
            _state.value = result
        }
    }

    fun decrypt(ciphertext: String, key: String) = viewModelScope.launch {
        keyData.value = key
        decryptVigenereCipherUseCase(ciphertext, key).collect {
            val result = when(it) {
                is ResultState.Success -> {
                    ResultState.Success(
                        ResultData(
                            plaintext = it.data,
                            key = keyData.value,
                            ciphertext = ciphertext,
                            isDecrypt = true
                        )
                    )
                }
                is ResultState.Error -> ResultState.Error(it.message)
                is ResultState.Loading -> ResultState.Loading
                else -> ResultState.Idle
            }
            _state.value = result
        }
    }

    fun resetState() {
        _state.value = ResultState.Idle
    }

}