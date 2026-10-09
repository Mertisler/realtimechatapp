package com.loc.realtimechatapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.loc.realtimechatapp.data.local.entity.MessageEntity
import com.loc.realtimechatapp.data.local.dao.MessageDao

@Database(
    entities = [MessageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ChatDatabase : RoomDatabase() {

    // Hilt üzerinden bu DAO'yu alıp Repository'e enjekte edeceğiz.
    abstract val dao: MessageDao
}
