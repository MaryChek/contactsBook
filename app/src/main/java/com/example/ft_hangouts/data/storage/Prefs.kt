package com.example.ft_hangouts.data.storage

import android.content.Context
import androidx.core.content.edit

class Prefs(context: Context) {
    private val sp = context.getSharedPreferences(NAME_STORAGE, Context.MODE_PRIVATE)

    fun putInt(key: String, value: Int) =
        sp.edit(true) {
            this.putInt(key, value)
        }

    fun getInt(key: String, default: Int) =
        sp.getInt(key, default)

//    fun putBoolean(key: String, value: Boolean) =
//        sp.edit(true) {
//            putBoolean(key, value)
//        }
//
//    fun getBoolean(key: String, default: Boolean) =
//        sp.getBoolean(key, default)

    companion object {
        const val NAME_STORAGE = "settings"
    }
}