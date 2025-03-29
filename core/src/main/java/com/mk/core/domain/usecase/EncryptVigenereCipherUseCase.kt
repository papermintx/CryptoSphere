package com.mk.core.domain.usecase

import com.mk.core.domain.repository.CryptoSphereRepository

class EncryptVigenereCipherUseCase(
    private val cryptoSphereRepository: CryptoSphereRepository
) {

    operator fun invoke(plaintext: String, key: String) = cryptoSphereRepository.encryptVigereCipher(plaintext, key)
}