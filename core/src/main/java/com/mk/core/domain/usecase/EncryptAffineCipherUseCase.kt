package com.mk.core.domain.usecase

import com.mk.core.domain.repository.CryptoSphereRepository

class EncryptAffineCipherUseCase (
    private val repository: CryptoSphereRepository
) {
    operator fun invoke(plaintext: String, key: Pair<Int, Int>) = repository.encryptAffineCipher(plaintext, key)
}
