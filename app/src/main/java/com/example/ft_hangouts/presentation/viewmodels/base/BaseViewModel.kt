package com.example.ft_hangouts.presentation.viewmodels.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation
import com.example.ft_hangouts.presentation.views.controllers.SingleEventLiveData

abstract class BaseViewModel<Model : Any, Navigation : BaseNavigation>(
    initModel: Model
) : ViewModel() {
    protected var model: Model = initModel

    private val actionMutableLiveData: MutableLiveData<Navigation> = SingleEventLiveData()

    val modelUpdated = MutableLiveData<Model>()
    val actionUpdated: LiveData<Navigation> = actionMutableLiveData

    protected fun updateScreen() {
        modelUpdated.value = model
    }

    fun goToScreen(destination: Navigation) {
        actionMutableLiveData.value = destination
    }

    abstract fun goToPrevious()
}