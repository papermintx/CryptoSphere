package com.mk.core.domain.usecase

import com.mk.core.domain.repository.CryptoSphereRepository

class EncryptHillCipherUseCase(
    private val cryptoSphereRepository: CryptoSphereRepository,
) {

    operator fun invoke(
        plaintext: String,
        key: Array<IntArray>
    ) = cryptoSphereRepository.encryptHillCipher(plaintext, key)

}