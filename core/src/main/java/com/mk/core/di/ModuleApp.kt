package com.mk.core.di

import android.content.Context
import com.mk.core.data.repository.CryptoSphereRepositoryImpl
import com.mk.core.domain.repository.CryptoSphereRepository
import com.mk.core.domain.usecase.DecryptAffineCipherUseCase
import com.mk.core.domain.usecase.DecryptAutoKeyVigenereCipherUseCase
import com.mk.core.domain.usecase.DecryptExtendedVigereCipherUseCase
import com.mk.core.domain.usecase.DecryptPlayfairCipherUseCase
import com.mk.core.domain.usecase.DecryptVigenereCipherUseCase
import com.mk.core.domain.usecase.EncryptAffineCipherUseCase
import com.mk.core.domain.usecase.EncryptAutoKeyVigenereCipherUseCase
import com.mk.core.domain.usecase.EncryptExtendedVigereCipherUseCase
import com.mk.core.domain.usecase.EncryptPlayfairCipherUseCase
import com.mk.core.domain.usecase.EncryptVigenereCipherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleApp {

    @Provides
    @Singleton
    fun provideCryptoSphereRepository(@ApplicationContext context: Context): CryptoSphereRepository {
        return CryptoSphereRepositoryImpl(context)
    }

    @Provides
    fun provideEncryptExtendedVigereCipherUseCase(cryptoSphereRepository: CryptoSphereRepository) =
        EncryptExtendedVigereCipherUseCase(cryptoSphereRepository)

    @Provides
    fun provideDecryptExtendedVigereCipherUseCase(cryptoSphereRepository: CryptoSphereRepository) =
        DecryptExtendedVigereCipherUseCase(cryptoSphereRepository)


    @Provides
    fun provideEncryptVigenereCipherUseCase(cryptoSphereRepository: CryptoSphereRepository) =
        EncryptVigenereCipherUseCase(cryptoSphereRepository)

    @Provides
    fun provideDecryptVigenereCipherUseCase(cryptoSphereRepository: CryptoSphereRepository) =
        DecryptVigenereCipherUseCase(cryptoSphereRepository)

    @Provides
    fun provideEncryptAutoKeyVigenereCipherUseCase(cryptoSphereRepository: CryptoSphereRepository) =
        EncryptAutoKeyVigenereCipherUseCase(cryptoSphereRepository)

    @Provides
    fun provideDecryptAutoKeyVigenereCipherUseCase(cryptoSphereRepository: CryptoSphereRepository) =
        DecryptAutoKeyVigenereCipherUseCase(cryptoSphereRepository)

    @Provides
    fun provideEncryptAffineCipherUseCase(cryptoSphereRepository: CryptoSphereRepository) =
        EncryptAffineCipherUseCase(cryptoSphereRepository)

    @Provides
    fun provideDecryptAffineCipherUseCase(cryptoSphereRepository: CryptoSphereRepository) =
        DecryptAffineCipherUseCase(cryptoSphereRepository)

    @Provides
    fun provideEncryptPlayfairCipherUseCase(cryptoSphereRepository: CryptoSphereRepository) =
        EncryptPlayfairCipherUseCase(cryptoSphereRepository)

    @Provides
    fun provideDecryptPlayfairCipherUseCase(cryptoSphereRepository: CryptoSphereRepository) =
        DecryptPlayfairCipherUseCase(cryptoSphereRepository)
}