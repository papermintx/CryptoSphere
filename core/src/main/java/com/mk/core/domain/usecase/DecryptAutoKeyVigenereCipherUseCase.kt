package com.mk.core.domain.usecase

import com.mk.core.domain.repository.CryptoSphereRepository

class DecryptAutoKeyVigenereCipherUseCase(
    private val repository: CryptoSphereRepository
) {
    operator fun invoke(ciphertext: String, key: String) = repository.decryptAutoKeyVigereCipher(ciphertext, key)
}