package com.mk.cryptosphere.ui.presentation.affine_cipher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.core.domain.model.ResultState
import com.mk.core.domain.usecase.DecryptAffineCipherUseCase
import com.mk.core.domain.usecase.EncryptAffineCipherUseCase
import com.mk.cryptosphere.common.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AffineCipherViewMode @Inject constructor(
    private val encryptAffineCipherUseCase: EncryptAffineCipherUseCase,
    private val decryptAffineCipherUseCase: DecryptAffineCipherUseCase
): ViewModel(){
    private val _state = MutableStateFlow<ResultState<ResultData>>(ResultState.Idle)
    val state = _state.asStateFlow()

    val plaintextData = MutableStateFlow<String>("")
    val keyData = MutableStateFlow<Pair<String, String>>(Pair("", ""))

    fun encrypt(plaintext: String, keyA: Int, keyB: Int) = viewModelScope.launch {
        plaintextData.value = plaintext
        keyData.value = Pair(keyA.toString(), keyB.toString())
        encryptAffineCipherUseCase(plaintext, keyA.toInt() to keyB.toInt()).collect {
            val result = when(it) {
                is ResultState.Success -> {
                    ResultState.Success(
                        ResultData(
                            plaintext = plaintextData.value,
                            keyA = keyData.value.first,
                            keyB = keyData.value.second,
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

    fun decrypt(ciphertext: String, keyA: Int, keyB: Int) = viewModelScope.launch {
        keyData.value =  Pair(keyA.toString(), keyB.toString())
        decryptAffineCipherUseCase(ciphertext, keyA.toInt() to keyB.toInt()).collect {
            val result = when(it) {
                is ResultState.Success -> {
                    ResultState.Success(
                        ResultData(
                            plaintext = it.data,
                            keyA = keyData.value.first,
                            keyB = keyData.value.second,
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