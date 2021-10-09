package com.example.ft_hangouts.presentation.viewmodels

import com.example.ft_hangouts.domain.interactors.ColorInteractor
import com.example.ft_hangouts.getSeconds
import com.example.ft_hangouts.presentation.navigation.FromMainActivity
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel

class RootViewModel(
    interactor: ColorInteractor,
) : BaseViewModel<Any, FromMainActivity>(interactor, Any()) {

    private var backgroundTime: Int? = null

    fun onActivityResume() {
        backgroundTime?.let { time ->
            val howTime: Int = getSeconds() - time
            updateAction(FromMainActivity.Navigate.ShowToast(howTime))
        }
    }

    fun onActivityPause() {
        backgroundTime = getSeconds()
    }

    override fun goToPrevious() = Unit
}
