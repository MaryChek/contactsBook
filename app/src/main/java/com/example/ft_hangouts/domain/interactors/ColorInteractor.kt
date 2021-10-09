package com.example.ft_hangouts.domain.interactors

import com.example.ft_hangouts.presentation.models.ColorState

interface ColorInteractor {
    fun getColor(): ColorState.Color

    fun setColor(color: ColorState.Color)
}