package com.mk.core.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.mk.core.data.room.model.EncryptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EncryptDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEncrypt(entity: EncryptEntity)

    @RawQuery(observedEntities = [EncryptEntity::class])
    fun getSortedHistory(query: SupportSQLiteQuery): Flow<List<EncryptEntity>>

    @Query("DELETE FROM encrypt_table WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM encrypt_table WHERE cipherName = :cipherName")
    suspend fun deleteByCipher(cipherName: String)
}

