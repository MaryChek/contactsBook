package com.example.ft_hangouts.data.room.model

import android.provider.BaseColumns

object ContactChatContract {
    object ChatEntry: BaseColumns {
        const val TABLE_NAME = "chat"
        const val COLUMN_CHAT_ID = "chat_id"
//        const val COLUMN_RECIPIENT_FIRST_NAME = "recipient_first_name"
//        const val COLUMN_RECIPIENT_LAST_NAME = "recipient_last_name"
//        const val COLUMN_NUMBER = "number"
        const val COLUMN_USER_TYPE = "user_type"
        const val COLUMN_CONTENT = "message"
        const val COLUMN_DATA = "data"
    }
}