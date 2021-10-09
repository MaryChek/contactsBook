package com.example.ft_hangouts.domain.models

import androidx.annotation.ColorRes
import com.example.ft_hangouts.R

class ColorState(
    val color: Color
) {

    @ColorRes val colorDarkResId: Int =
        when (color) {
            Color.Purple -> R.color.colorPurpleDark
            Color.Blue -> R.color.colorBlueDark
            Color.Red -> R.color.colorRedDark
            Color.Green -> R.color.colorGreenDark
        }

    @ColorRes val colorResId: Int =
        when (color) {
            Color.Purple -> R.color.colorPurple
            Color.Blue -> R.color.colorBlue
            Color.Red -> R.color.colorRed
            Color.Green -> R.color.colorGreen
        }

    enum class Color {
        Purple,
        Blue,
        Red,
        Green
    }

    fun isNotPurple(): Boolean =
        color != Color.Purple

    fun isNotBlue(): Boolean =
        color != Color.Blue

    fun isNotRed(): Boolean =
        color != Color.Red

    fun isNotGreen(): Boolean =
        color != Color.Green
}