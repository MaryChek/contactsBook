package com.example.ft_hangouts.presentation.viewmodels

import android.net.Uri
import android.util.Log
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.mappers.ContactMapper
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel

class ContactCreatorViewModel(
    private val interactor: ContactsInteractor,
    private val mapper: ContactMapper,
) : BaseContactEditorViewModel<FromContactCreator>() {

    override val logTag: String = this::class.java.simpleName

    fun init() {
        val contactId: String = interactor.getNewContactIndex()
        updateModel(contactId)
    }

    override fun onContactCorrect() {
        if (model.hesImage) {
            goToScreen(FromContactCreator.Command.AccessWritePermissions)
        }
        saveContact()
    }

    private fun saveContact() {
        model.contact?.let { contact ->
            interactor.addContact(mapper.mapContact(contact))
        }
        goToPrevious()
    }

    override fun onAddPhotoClick() {
        goToScreen(FromContactCreator.Command.AccessReadPermissions)
    }

    override fun isNumberIndividual(number: String): Boolean =
        interactor.isNumberIndividual(number)

    override fun onReadStoragePermissionIsGranted() {
        goToScreen(FromContactCreator.Command.TakePictureFromGallery)
    }

    override fun onTakePicture(imageUri: Uri) {
        Log.v("IMAGE", imageUri.toString()) // TODO add picture to imageView
    }

    override fun onReadStoragePermissionIsNotGranted() {

    }

    override fun onWriteStoragePermissionIsGranted() {
        //TODO save image
    }

    override fun onWriteStoragePermissionIsNotGranted() {

    }

    override fun goToPrevious() =
        goToScreen(FromContactCreator.Navigate.PreviousScreen)

    override fun showMessageError() =
        goToScreen(FromContactCreator.Command.ShowErrorMessage(model.errorMessageResId))
}