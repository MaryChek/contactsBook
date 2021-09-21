package com.example.ft_hangouts.data.room.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.ft_hangouts.data.room.helper.ContactDBHelper
import com.example.ft_hangouts.data.room.model.ContactChatContract.ChatEntry
import com.example.ft_hangouts.data.room.model.ChatMessage

class DBMessages(private val dbHelper: ContactDBHelper) : DBMessagesDao {

    override fun getAllMessages(contactId: String): List<ChatMessage> {
        val selectQuery = "SELECT * FROM `${ChatEntry.TABLE_NAME}` " +
                "WHERE `${ChatEntry.COLUMN_CHAT_ID}` = $contactId"
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        val messages = ArrayList<ChatMessage>()
        while (cursor.moveToNext()) {
            val message: String = cursor.getString(cursor.getColumnIndex(ChatEntry.COLUMN_CONTENT))
            val userType: Int = cursor.getInt(cursor.getColumnIndex(ChatEntry.COLUMN_USER_TYPE))
            val data: String = cursor.getString(cursor.getColumnIndex(ChatEntry.COLUMN_DATA))
            messages.add(ChatMessage(message, userType, data))
        }
        cursor.close()
        db.close()

        return messages
    }

    override fun addMessageById(message: ChatMessage, contactId: String) {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values = getContentValues(message, contactId)
        db.insert(ChatEntry.TABLE_NAME, null, values)
        db.close()
    }

    override fun removeChatById(contactId: String) {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        db.delete(ChatEntry.TABLE_NAME, ("`${ChatEntry.COLUMN_CHAT_ID}` = ?"), arrayOf(contactId))
        db.close()
    }

    private fun getContentValues(message: ChatMessage, contactId: String): ContentValues {
        val values = ContentValues()

        values.put(ChatEntry.COLUMN_CHAT_ID, contactId)
        values.put(ChatEntry.COLUMN_USER_TYPE, message.userType)
        values.put(ChatEntry.COLUMN_DATA, message.data)
        values.put(ChatEntry.COLUMN_CONTENT, message.messageText)

        return values
    }
}