package com.example.ft_hangouts.presentation.viewmodels.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ft_hangouts.presentation.navigation.BaseNavigation
import com.example.ft_hangouts.presentation.navigation.GoToScreen

abstract class BaseViewModel<Model : Any, Navigation : BaseNavigation>(initModel: Model) :
    ViewModel(), GoToScreen<Navigation> {
    protected var model: Model = initModel

    val modelUpdated = MutableLiveData<Model>()
    val navigationUpdated = MutableLiveData<Navigation>()

    protected fun updateScreen() {
        modelUpdated.value = model
    }

    final override fun goToScreen(destination: Navigation) {
        navigationUpdated.value = destination
    }

    abstract fun goToPrevious()
}