package com.example.ft_hangouts

import android.annotation.SuppressLint
import androidx.appcompat.app.ActionBar
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
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

fun ActionBar.changeColor(context: Context, @ColorRes colorResId: Int) {
    setBackgroundDrawable(ColorDrawable(context.getColor(colorResId)))
}

fun Fragment.getColor(@ColorRes colorResId: Int): Int =
    requireContext().getColor(colorResId)

fun ImageView.updateColor(color: Int) {
    colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
}