package com.example.ft_hangouts.data.storage

class ColorStorage(private val prefs: Prefs) {
    fun getColor(default: Int) =
        prefs.getInt(COLOR_KEY, default)

    fun setColor(value: Int) =
        prefs.putInt(COLOR_KEY, value)

    companion object {
        private const val COLOR_KEY = "color"
    }
}