package com.mk.cryptosphere.ui.presentation.extended_vigenere_cipher

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.core.domain.model.ResultState
import com.mk.core.domain.usecase.DecryptExtendedVigereCipherUseCase
import com.mk.core.domain.usecase.EncryptExtendedVigereCipherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExtendedVigereCipherViewModel @Inject constructor(
    private val encryptExtendedVigereCipherUseCase: EncryptExtendedVigereCipherUseCase,
    private val decryptExtendedVigereCipherUseCase: DecryptExtendedVigereCipherUseCase
): ViewModel() {

    private val _state  = MutableStateFlow<ResultState<Uri>>(ResultState.Idle)
    val state = _state.asStateFlow()


    fun encrypt(uri: Uri, key: String) {
        viewModelScope.launch {
           encryptExtendedVigereCipherUseCase(uri, key).collect {
               _state.value = it
           }
        }
    }

    fun decrypt(uri: Uri, key: String) {
        viewModelScope.launch {
            decryptExtendedVigereCipherUseCase(uri, key).collect {
                _state.value = it
            }
        }
    }

    fun resetState() {
        _state.value = ResultState.Idle
    }
}