package com.mk.core.domain.usecase

import com.mk.core.domain.repository.CryptoSphereRepository

class DeleteHistoryUseCase(
    private val cryptoSphereRepository: CryptoSphereRepository
) {
    suspend operator fun invoke(id: Int) {
        cryptoSphereRepository.deleteHistoryById(id)
    }
}