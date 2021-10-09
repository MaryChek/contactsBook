package com.example.ft_hangouts.presentation.fragments.base

import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.ft_hangouts.R
import com.example.ft_hangouts.changeColor
import com.example.ft_hangouts.getColor
import com.example.ft_hangouts.presentation.App
import com.example.ft_hangouts.presentation.activities.MainActivity
import com.example.ft_hangouts.presentation.models.ColorState
import com.example.ft_hangouts.presentation.navigation.base.BaseNavigation
import com.example.ft_hangouts.presentation.navigation.base.GoToScreen
import com.example.ft_hangouts.presentation.viewmodels.base.BaseViewModel
import java.lang.IllegalStateException

abstract class BaseViewModelFragment<
        Model : Any,
        FromScreen : BaseNavigation,
        Navigate: BaseNavigation.Navigate,
        Router : GoToScreen<Navigate>,
        ViewModel : BaseViewModel<Model, FromScreen>>
    : Fragment() {

    open val logTag: String = this::class.java.simpleName

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
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
//        menu.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                viewModel.goToPrevious()
            R.id.changeToRed ->
                viewModel.onRedColorSelected()
            R.id.changeToBlue ->
                viewModel.onBlueColorSelected()
            R.id.changeToGreen ->
                viewModel.onGreenColorSelected()
            R.id.changeToPurple ->
                viewModel.onPurpleColorSelected()
            else -> return false
        }
        return true
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
        viewModel.colorStateUpdated.observe(viewLifecycleOwner, Observer(this::updateColor))
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

    @CallSuper
    open fun updateColor(colorState: ColorState) {
        updateActionBarColor(colorState)
        updateStatusBarColor(colorState)
    }

    private fun updateActionBarColor(colorState: ColorState) =
        (activity as MainActivity).supportActionBar
            ?.changeColor(requireContext(), colorState.colorResId)

    private fun updateStatusBarColor(colorState: ColorState) {
         val window: Window? = activity?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = getColor(colorState.colorDarkResId)
    }

    abstract fun getViewModelClass(): Class<ViewModel>

    abstract fun getNavRouter(): Router

    protected fun accessPermission(
        permissionLauncher: ActivityResultLauncher<String>,
        permission: String,
        onResponseSuccess: () -> Unit,
    ) {
        try {
            if (isPermissionDenied(permission)) {
                permissionLauncher.launch(permission)
            } else {
                onResponseSuccess()
            }
        } catch (e: IllegalStateException) {
            Log.e(logTag, "Context is null", e)
            //TODO show something wrong try again
        }
    }

    private fun isPermissionDenied(permission: String): Boolean =
        ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_DENIED

    protected fun showErrorMessage(@StringRes errorMessageResId: Int) {
        val message: String = getString(errorMessageResId)
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}