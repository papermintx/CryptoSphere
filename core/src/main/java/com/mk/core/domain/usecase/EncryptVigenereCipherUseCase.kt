package com.mk.core.domain.usecase

import com.mk.core.domain.model.ResultState
import com.mk.core.domain.repository.CryptoSphereRepository
import kotlinx.coroutines.flow.Flow

class EncryptVigenereCipherUseCase(
    private val cryptoSphereRepository: CryptoSphereRepository
) {

    operator fun invoke(plaintext: String, key: String): Flow<ResultState<String>> {
        return cryptoSphereRepository.encryptVigenereCipher(plaintext, key)
    }
}