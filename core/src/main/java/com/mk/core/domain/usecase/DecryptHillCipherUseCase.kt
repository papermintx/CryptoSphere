package com.mk.core.domain.usecase

import com.mk.core.domain.repository.CryptoSphereRepository

class DecryptHillCipherUseCase(
    private val repository: CryptoSphereRepository
) {
    operator fun invoke(ciphertext: String, key: Array<IntArray>) =
        repository.decryptHillCipher(ciphertext, key)
}