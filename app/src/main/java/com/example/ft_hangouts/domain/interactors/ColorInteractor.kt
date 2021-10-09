package com.example.ft_hangouts.domain.interactors

import com.example.ft_hangouts.data.storage.ColorStorage
import com.example.ft_hangouts.domain.models.ColorState

class ColorInteractor(private val colorStorage: ColorStorage) {
    fun getColor(): ColorState.Color {
        val color: Int = colorStorage.getColor(DEFAULT_COLOR_VALUE.ordinal)
        return mapColor(color)
    }

    private fun mapColor(color: Int): ColorState.Color =
        when (color) {
            ColorState.Color.Green.ordinal -> ColorState.Color.Green
            ColorState.Color.Blue.ordinal -> ColorState.Color.Blue
            ColorState.Color.Red.ordinal -> ColorState.Color.Red
            else -> ColorState.Color.Purple
        }

    fun setColor(color: ColorState.Color) =
        colorStorage.setColor(color.ordinal)

    companion object {
        private val DEFAULT_COLOR_VALUE = ColorState.Color.Purple
    }
}