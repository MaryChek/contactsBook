package com.example.ft_hangouts

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

fun getSeconds(): Int {
    val cal: Calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+3:00"))
    val hour = cal.get(Calendar.HOUR)
    val minute = cal.get(Calendar.MINUTE)
    val second = cal.get(Calendar.SECOND)

    return hour * 3600 + minute * 60 + second
}

@SuppressLint("SimpleDateFormat")
fun Date.toSimpleDate(): String {
    val formatter = SimpleDateFormat("dd MMM',' HH:mm")
    return formatter.format(this)
}