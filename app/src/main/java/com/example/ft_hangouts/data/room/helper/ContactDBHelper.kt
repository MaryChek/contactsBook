package com.example.ft_hangouts.data.room.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.ft_hangouts.data.room.model.ContactInformationContract.ContactEntry
import com.example.ft_hangouts.data.room.model.ContactChatContract.ChatEntry

class ContactDBHelper(context: Context, dbName: String, dbVersion: Int) :
    SQLiteOpenHelper(context, dbName, null, dbVersion) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_CONTACTS_TABLE_QUERY)
        db?.execSQL(CREATE_CHAT_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val deleteContactsEntriesQuery = "DROP TABLE IF EXISTS " + ContactEntry.TABLE_NAME
        val deleteChatEntriesQuery = "DROP TABLE IF EXISTS " + ChatEntry.TABLE_NAME

        db?.execSQL(deleteContactsEntriesQuery)
        db?.execSQL(deleteChatEntriesQuery)
        onCreate(db)
    }

    companion object {
        private const val CREATE_CONTACTS_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS `${ContactEntry.TABLE_NAME}` (" +
                    "`${BaseColumns._ID}` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`${ContactEntry.COLUMN_FIRST_NAME}` VARCHAR(255), " +
                    "`${ContactEntry.COLUMN_LAST_NAME}` VARCHAR(255) NULL DEFAULT NULL, " +
                    "`${ContactEntry.COLUMN_NUMBER}` VARCHAR(255), " +
                    "`${ContactEntry.COLUMN_EMAIL}` VARCHAR(255) NULL DEFAULT NULL, " +
                    "`${ContactEntry.COLUMN_IMAGE}` VARCHAR(255) NULL DEFAULT NULL);"

        private const val CREATE_CHAT_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS `${ChatEntry.TABLE_NAME}` (" +
                    "`${BaseColumns._ID}` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`${ChatEntry.COLUMN_CHAT_ID}` INTEGER, " +
                    "`${ChatEntry.COLUMN_CONTENT}` VARCHAR(255), " +
                    "`${ChatEntry.COLUMN_DATA}` VARCHAR(255), " +
                    "`${ChatEntry.COLUMN_USER_TYPE}` INTEGER);"
    }
}