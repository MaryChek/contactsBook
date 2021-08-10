package com.example.ft_hangouts.data.room.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.ft_hangouts.data.room.model.ContactInformationContract.ContactEntry

class ContactDBHelper(context: Context, dbName: String, dbVersion: Int) :
    SQLiteOpenHelper(context, dbName, null, dbVersion) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE IF NOT EXISTS `${ContactEntry.TABLE_NAME}` (" +
                        "`${BaseColumns._ID}` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "`${ContactEntry.COLUMN_FIRST_NAME}` VARCHAR(255), " +
                        "`${ContactEntry.COLUMN_LAST_NAME}` VARCHAR(255) NULL DEFAULT NULL, " +
                        "`${ContactEntry.COLUMN_NUMBER}` VARCHAR(255), " +
                        "`${ContactEntry.COLUMN_EMAIL}` VARCHAR(255) NULL DEFAULT NULL, " +
                        "`${ContactEntry.COLUMN_IMAGE}` VARCHAR(255) NULL DEFAULT NULL);"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val deleteEntriesQuery = "DROP TABLE IF EXISTS " + ContactEntry.TABLE_NAME
        db?.execSQL(deleteEntriesQuery)
        onCreate(db)
    }
}