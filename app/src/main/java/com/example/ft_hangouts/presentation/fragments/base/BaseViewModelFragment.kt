package com.example.ft_hangouts.presentation.fragments.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.ft_hangouts.presentation.App
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation
import com.example.ft_hangouts.presentation.navigation.base.GoToScreen
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel

abstract class BaseViewModelFragment<
        Model : Any,
        FromScreen : BaseNavigation,
        Navigate: BaseNavigation.Navigate,
        Router : GoToScreen<Navigate>,
        ViewModel : BaseViewModel<Model, FromScreen>>
    : Fragment() {

    protected open lateinit var router: Router
    protected lateinit var viewModel: ViewModel
    protected lateinit var navController: NavController
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
    }

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
        navController = Navigation.findNavController(view)
        setupObservers()
        router = getNavRouter()
        initOnBackPressedListener()
    }

    private fun setupObservers() {
        viewModel.actionUpdated.observe(viewLifecycleOwner, Observer(this::navigateTo))
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

    abstract fun navigateTo(destination: FromScreen)
//
//    protected fun navigate(@IdRes navigateToId: Int, arguments: Bundle? = null) =
//        findNavController().navigate(navigateToId, arguments)
//
//    protected open fun navigateToPrevious(@IdRes navigateToId: Int) =
//        findNavController().popBackStack(navigateToId, true)
//
//    protected open fun navigateToPrevious() =
//        findNavController().navigate(R.id.go_to_previous)

    protected open fun updateScreen(model: Model) =
        Unit

    abstract fun getViewModelClass(): Class<ViewModel>

    abstract fun getNavRouter(): Router
}