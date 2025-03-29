package com.mk.core.domain.usecase

import com.mk.core.domain.repository.CryptoSphereRepository

class EncryptPlayfairCipherUseCase (
    private val cryptoSphereRepository: CryptoSphereRepository
) {

    operator fun invoke(plaintext: String, key: String) =
        cryptoSphereRepository.encryptPlayfairCipher(plaintext, key)
}