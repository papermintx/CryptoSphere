package com.mk.core.domain.usecase

import com.mk.core.domain.repository.CryptoSphereRepository

class EncryptAutoKeyVigenereCipherUseCase (
    private val repository: CryptoSphereRepository
) {
    operator fun invoke(plaintext: String, key: String) = repository.encryptAutoKeyVigereCipher(plaintext, key)
}