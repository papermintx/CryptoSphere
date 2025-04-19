package com.mk.cryptosphere.ui.presentation.vigenere_cipher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.core.data.room.model.EncryptEntity
import com.mk.core.domain.model.ResultState
import com.mk.core.domain.usecase.DecryptVigenereCipherUseCase
import com.mk.core.domain.usecase.DeleteHistoryUseCase
import com.mk.core.domain.usecase.EncryptVigenereCipherUseCase
import com.mk.core.domain.usecase.GetHistoryUseCase
import com.mk.core.domain.usecase.InsertHistoryUseCase
import com.mk.core.utils.CipherAlgorithm
import com.mk.cryptosphere.common.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VigenereCipherViewModel @Inject constructor(
    private val encryptVigenereCipherUseCase: EncryptVigenereCipherUseCase,
    private val decryptVigenereCipherUseCase: DecryptVigenereCipherUseCase,
    private val insertHistoryUseCase: InsertHistoryUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase
): ViewModel() {

    private val _state = MutableStateFlow<ResultState<ResultData>>(ResultState.Idle)
    val state = _state.asStateFlow()

    private val _historyState = MutableStateFlow<ResultState<List<EncryptEntity>>>(ResultState.Idle)
    val historyState = _historyState.asStateFlow()

    val plaintextData = MutableStateFlow<String>("")
    val keyData = MutableStateFlow<String>("")


    fun encrypt(plaintext: String, key: String) = viewModelScope.launch {
        plaintextData.value = plaintext
        keyData.value = key
        encryptVigenereCipherUseCase(plaintext, key).collect {
            val result = when(it) {
                is ResultState.Success -> {
                    val successResult = ResultState.Success(
                        ResultData(
                            plaintext = plaintextData.value,
                            key = keyData.value,
                            ciphertext = it.data,
                            isDecrypt = false
                        )
                    )
                    val entity = EncryptEntity(
                        cipherName = CipherAlgorithm.VIGENERE_CIPHER.toString(),
                        plaintext = plaintextData.value,
                        key = keyData.value,
                        ciphertext = it.data,
                        algorithm = CipherAlgorithm.VIGENERE_CIPHER.toString(),
                    )
                    successResult
                }
                is ResultState.Error -> ResultState.Error(it.message)
                is ResultState.Loading -> ResultState.Loading
                else -> ResultState.Idle
            }
            _state.value = result
        }
    }

    fun deleteHistory(id: Int) = viewModelScope.launch {
        deleteHistoryUseCase(id)
    }

    fun getHistory(cipherName: String, sortedDesc: Boolean) = viewModelScope.launch {
        _historyState.value = ResultState.Loading

        getHistoryUseCase(cipherName, sortedDesc).collect { result ->
            if (result.isNotEmpty()) {
                _historyState.value = ResultState.Success(result)
            } else {
                _historyState.value = ResultState.NothingData
            }
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

    fun saveHistory() = viewModelScope.launch {
        val currentState = _state.value
        if (currentState is ResultState.Success) {
            val data = currentState.data

            if (!data.isDecrypt) {
                val entity = EncryptEntity(
                    cipherName = CipherAlgorithm.VIGENERE_CIPHER.toString(),
                    plaintext = data.plaintext,
                    key = data.key,
                    ciphertext = data.ciphertext,
                    algorithm = CipherAlgorithm.VIGENERE_CIPHER.toString()
                )
                insertHistoryUseCase(entity)
            }
        }
    }


    fun resetState() {
        _state.value = ResultState.Idle
    }

}