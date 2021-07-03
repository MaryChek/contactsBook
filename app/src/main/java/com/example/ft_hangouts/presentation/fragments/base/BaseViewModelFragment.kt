package com.example.ft_hangouts.presentation.fragments.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ft_hangouts.presentation.App
import com.example.ft_hangouts.presentation.navigation.BaseNavigation
import com.example.ft_hangouts.presentation.navigation.GoToScreen
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel

abstract class BaseViewModelFragment<
        Model : Any,
        Navigation : BaseNavigation,
        ViewModel : BaseViewModel<Model, Navigation>>
    : Fragment(), GoToScreen<Navigation> {

    protected lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        initViewModel()
    }

    @CallSuper
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

    @CallSuper
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                viewModel.goToPrevious()
                true
            }
            else -> false
        }

    private fun initViewModel() {
        val app: App = (requireActivity().applicationContext as App)
        val viewModelFactory = app.viewModelFactory
        val viewModelProvider = ViewModelProvider(this, viewModelFactory)
        viewModel = viewModelProvider.get(getViewModelClass())
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        initOnBackPressedListener()
    }

    private fun setupObservers() {
        viewModel.navigationUpdated.observe(viewLifecycleOwner, Observer(this::goToScreen))
        viewModel.modelUpdated.observe(viewLifecycleOwner, Observer(this::updateScreen))
    }

    private fun initOnBackPressedListener() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.goToPrevious()
            }
        }
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    protected fun navigate(@IdRes navigateToId: Int, arguments: Bundle? = null) {
        findNavController().navigate(navigateToId, arguments)
//        viewModel.clearNavigate()
    }

    protected open fun navigateToPrevious() {
        findNavController().popBackStack()
    }

    protected open fun updateScreen(model: Model) =
        Unit

    abstract fun getViewModelClass(): Class<ViewModel>
}