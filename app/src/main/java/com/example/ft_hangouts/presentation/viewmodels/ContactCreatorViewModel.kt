package com.example.ft_hangouts.presentation.viewmodels

import android.net.Uri
import android.util.Log
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.mappers.ContactMapper
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel

class ContactCreatorViewModel(
    interactor: ContactsInteractor,
    private val mapper: ContactMapper,
) : BaseContactEditorViewModel<FromContactCreator>(interactor) {

    override val logTag: String = this::class.java.simpleName

    override fun init() {
        super.init()
        val contactId: String = interactor.getNewContactIndex()
        updateModel(contactId)
    }

    override fun onContactCorrect() {
        if (model.hesImage) {
            updateAction(FromContactCreator.Command.AccessWritePermissions)
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
        updateAction(FromContactCreator.Command.AccessReadPermissions)
    }

    override fun isNumberIndividual(number: String): Boolean =
        interactor.isNumberIndividual(number)

    override fun onReadStoragePermissionResponse(isGranted: Boolean) {
        if (isGranted) {
            updateAction(FromContactCreator.Command.TakePictureFromGallery)
        } else {
            //TODO do something
        }
    }

    override fun onWriteStoragePermissionResponse(isGranted: Boolean) {
        if (isGranted) {
            //TODO do something
        } else {
            //TODO do something
        }
    }

    override fun onTakePicture(imageUri: Uri) {
        Log.v("IMAGE", imageUri.toString()) // TODO add picture to imageView
    }

    override fun goToPrevious() =
        updateAction(FromContactCreator.Navigate.PreviousScreen)

    override fun showMessageError() =
        updateAction(FromContactCreator.Command.ShowErrorMessage(model.errorMessageResId))
}