package com.example.ft_hangouts.data.room.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.ft_hangouts.data.room.helper.ContactDBHelper
import com.example.ft_hangouts.data.room.model.ContactInformationContract.ContactEntry
import com.example.ft_hangouts.domain.models.Contact

class DBContacts(private val dbHelper: ContactDBHelper) : DbContactsDao {

    override fun getAllContacts(): List<Contact> {
        val selectQuery = "SELECT * FROM `${ContactEntry.TABLE_NAME}` " +
                "ORDER BY `${ContactEntry.COLUMN_FIRST_NAME}` ASC"
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        val contacts = ArrayList<Contact>()
        while (cursor.moveToNext()) {
            val id: Int = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            val name: String =
                cursor.getString(cursor.getColumnIndex(ContactEntry.COLUMN_FIRST_NAME))
            val lastName: String? =
                cursor.getString(cursor.getColumnIndex(ContactEntry.COLUMN_LAST_NAME))
            val number: String = cursor.getString(cursor.getColumnIndex(ContactEntry.COLUMN_NUMBER))
            val email: String? = cursor.getString(cursor.getColumnIndex(ContactEntry.COLUMN_EMAIL))
            val image: String? = cursor.getString(cursor.getColumnIndex(ContactEntry.COLUMN_IMAGE))

            contacts.add(Contact(id.toString(), name, lastName, number, email, image))
        }
        cursor.close()
        db.close()

        return contacts
    }

    override fun addContact(contact: Contact): Long {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values = getContentValues(contact)

        val id = db.insert(ContactEntry.TABLE_NAME, null, values)
        db.close()
        return id
    }

    override fun removeContactById(contactId: String) {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        db.delete(ContactEntry.TABLE_NAME, ("`${BaseColumns._ID}` = ?"), arrayOf(contactId))
        db.close()
    }

    override fun updateContact(contact: Contact) {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values = getContentValues(contact)

        db.update(ContactEntry.TABLE_NAME, values, "`${BaseColumns._ID}` = ?", arrayOf(contact.id))
        db.close()
    }

    private fun getContentValues(contact: Contact): ContentValues {
        val values = ContentValues()

        values.put(ContactEntry.COLUMN_FIRST_NAME, contact.name)
        values.put(ContactEntry.COLUMN_LAST_NAME, contact.lastName)
        values.put(ContactEntry.COLUMN_NUMBER, contact.number)
        values.put(ContactEntry.COLUMN_EMAIL, contact.email)
        values.put(ContactEntry.COLUMN_IMAGE, contact.imagePath)

        return values
    }
}