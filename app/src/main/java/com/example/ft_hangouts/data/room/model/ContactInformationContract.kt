package com.example.ft_hangouts.data.room.model

import android.provider.BaseColumns

object ContactInformationContract {
    object ContactEntry: BaseColumns {
        const val TABLE_NAME = "contacts"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_NUMBER = "number"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_IMAGE = "image"
    }
}