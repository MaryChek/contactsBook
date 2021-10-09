package com.example.ft_hangouts.presentation.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.ft_hangouts.presentation.fragments.base.BaseContactEditorFragment
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.navigation.router.ContactCreatorRouter
import com.example.ft_hangouts.presentation.viewmodels.ContactCreatorViewModel
import java.lang.IllegalStateException

class ContactCreatorFragment : BaseContactEditorFragment<
        FromContactCreator, FromContactCreator.Navigate,
        ContactCreatorRouter, ContactCreatorViewModel>() {

    override val logTag: String = this::class.java.simpleName

    override fun getViewModelClass(): Class<ContactCreatorViewModel> =
        ContactCreatorViewModel::class.java

    override fun getNavRouter(): ContactCreatorRouter =
        ContactCreatorRouter(navController)

    override fun navigateTo(destination: FromContactCreator) =
        when (destination) {
            is FromContactCreator.Command.ShowErrorMessage ->
                showErrorMessage(destination.errorMessageResId)
            is FromContactCreator.Command.AccessWritePermissions ->
                accessWritePermissions()
            is FromContactCreator.Command.AccessReadPermissions ->
                accessReadPermissions()
            is FromContactCreator.Command.TakePictureFromGallery ->
                takePicture()
            is FromContactCreator.Navigate ->
                router.goToScreen(destination)
        }

    private fun takePicture() {
        getContentLauncher?.launch(IMAGES_DIRECTORY)
            ?: Log.e(logTag, "get content launcher not init", IllegalStateException())
    }

    private fun accessReadPermissions() {
        requestPermissionForReadStorageLauncher?.let { launcher ->
            accessPermission(launcher, Manifest.permission.READ_EXTERNAL_STORAGE) {
                viewModel.onReadStoragePermissionResponse(true)
            }
        } ?: Log.e(logTag, "permission for read launcher not init", IllegalStateException())
    }

    private fun accessWritePermissions() {
        requestPermissionForWriteStorageLauncher?.let { launcher ->
            accessPermission(launcher, Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                viewModel.onReadStoragePermissionResponse(true)
            }
        } ?: Log.e(logTag, "permission for write launcher not init", IllegalStateException())
    }

    companion object {
        private const val IMAGES_DIRECTORY = "image/*"
    }
}