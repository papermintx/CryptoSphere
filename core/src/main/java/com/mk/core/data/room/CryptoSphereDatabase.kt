package com.mk.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mk.core.data.room.dao.EncryptDao
import com.mk.core.data.room.model.EncryptEntity

@Database(entities = [EncryptEntity::class], version = 1, exportSchema = false)
abstract class CryptoSphereDatabase: RoomDatabase() {

    abstract fun encryptDao(): EncryptDao
}