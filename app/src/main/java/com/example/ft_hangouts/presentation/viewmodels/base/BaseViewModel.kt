package com.example.ft_hangouts.presentation.viewmodels.base

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ft_hangouts.domain.interactors.ColorInteractor
import com.example.ft_hangouts.domain.models.ColorState
import com.example.ft_hangouts.domain.models.ColorState.Color
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation
import com.example.ft_hangouts.presentation.views.controllers.SingleEventLiveData

abstract class BaseViewModel<Model : Any, Navigation : BaseNavigation>(
    private val interactor: ColorInteractor, initModel: Model
) : ViewModel() {
    protected var model: Model = initModel

    private val actionMutableLiveData: MutableLiveData<Navigation> = SingleEventLiveData()

    val modelUpdated = MutableLiveData<Model>()
    val actionUpdated: LiveData<Navigation> = actionMutableLiveData
    val colorStateUpdated = MutableLiveData<ColorState>()
    protected var colorState = ColorState(Color.Purple)

    init {
        updateColorState()
    }

    @CallSuper
    open fun onViewCreated() {
        val color: Color = interactor.getColor()
        updateColorIfRequired(color)
    }

    private fun updateColorIfRequired(color: Color) =
        when (color) {
            Color.Red -> onRedColorSelected()
            Color.Blue -> onBlueColorSelected()
            Color.Green -> onGreenColorSelected()
            Color.Purple -> onPurpleColorSelected()
        }

    protected fun updateScreen() {
        modelUpdated.value = model
    }

    fun updateAction(destination: Navigation) {
        actionMutableLiveData.value = destination
    }

    abstract fun goToPrevious()

    private fun updateColorState() {
        colorStateUpdated.value = colorState
    }

    fun onRedColorSelected() {
        if (colorState.isNotRed()) {
            updateColor(Color.Red)
        } else {
            return
        }
    }

    fun onBlueColorSelected() {
        if (colorState.isNotBlue()) {
            updateColor(Color.Blue)
        } else {
            return
        }
    }

    fun onGreenColorSelected() {
        if (colorState.isNotGreen()) {
            updateColor(Color.Green)
        } else {
            return
        }
    }

    fun onPurpleColorSelected() {
        if (colorState.isNotPurple()) {
            updateColor(Color.Purple)
        } else {
            return
        }
    }

    private fun updateColor(color: Color) {
        colorState = ColorState(color)
        updateColorState()
        onColorUpdated()
    }

    @CallSuper
    open fun onColorUpdated() {
        interactor.setColor(colorState.color)
    }
}