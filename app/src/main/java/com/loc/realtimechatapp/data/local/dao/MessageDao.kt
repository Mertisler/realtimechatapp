package com.loc.realtimechatapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loc.realtimechatapp.data.local.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    // Mesajları zamana göre sıralı olarak getirir. Flow olduğu için tablo dinlenir.
    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<MessageEntity>>

    // Sunucudan gelen veya kullanıcının gönderdiği mesajı veritabanına yazar.
    // Aynı ID'li mesaj gelirse (çakışma), üzerine yazar.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)
}