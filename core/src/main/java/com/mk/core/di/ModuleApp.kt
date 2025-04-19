package com.mk.core.di

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import com.mk.core.data.repository.CryptoSphereRepositoryImpl
import com.mk.core.data.room.CryptoSphereDatabase
import com.mk.core.data.room.dao.EncryptDao
import com.mk.core.domain.repository.CryptoSphereRepository
import com.mk.core.domain.usecase.DecryptAffineCipherUseCase
import com.mk.core.domain.usecase.DecryptAutoKeyVigenereCipherUseCase
import com.mk.core.domain.usecase.DecryptExtendedVigereCipherUseCase
import com.mk.core.domain.usecase.DecryptPlayfairCipherUseCase
import com.mk.core.domain.usecase.DecryptVigenereCipherUseCase
import com.mk.core.domain.usecase.DeleteHistoryUseCase
import com.mk.core.domain.usecase.EncryptAffineCipherUseCase
import com.mk.core.domain.usecase.EncryptAutoKeyVigenereCipherUseCase
import com.mk.core.domain.usecase.EncryptExtendedVigereCipherUseCase
import com.mk.core.domain.usecase.EncryptPlayfairCipherUseCase
import com.mk.core.domain.usecase.EncryptVigenereCipherUseCase
import com.mk.core.domain.usecase.GetHistoryUseCase
import com.mk.core.domain.usecase.InsertHistoryUseCase
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
    fun provideStoryDatabase(@ApplicationContext context: Context): CryptoSphereDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CryptoSphereDatabase::class.java,
            "database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideEncryptDao(database: CryptoSphereDatabase): EncryptDao {
        return database.encryptDao()
    }


    @Provides
    @Singleton
    fun provideCryptoSphereRepository(@ApplicationContext context: Context, encryptDao: EncryptDao): CryptoSphereRepository {
        return CryptoSphereRepositoryImpl(context, encryptDao)
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

    @Provides
    fun provideInsertEncryptUseCase(cryptoSphereRepository: CryptoSphereRepository) =
        InsertHistoryUseCase(cryptoSphereRepository)

    @Provides
    fun provideGetEncryptUseCase(cryptoSphereRepository: CryptoSphereRepository) =
        GetHistoryUseCase(cryptoSphereRepository)

    @Provides
    fun provideDeleteEncryptUseCase(cryptoSphereRepository: CryptoSphereRepository) =
        DeleteHistoryUseCase(cryptoSphereRepository)
}