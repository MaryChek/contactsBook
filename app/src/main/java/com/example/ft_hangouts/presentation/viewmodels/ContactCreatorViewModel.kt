package com.example.ft_hangouts.presentation.viewmodels

import android.net.Uri
import android.util.Log
import com.example.ft_hangouts.domain.interactors.ContactsInteractor
import com.example.ft_hangouts.presentation.mappers.ContactMapper
import com.example.ft_hangouts.presentation.models.Contact
import com.example.ft_hangouts.presentation.models.ContactState
import com.example.ft_hangouts.presentation.navigation.FromContactCreator
import com.example.ft_hangouts.presentation.viewmodels.base.BaseContactEditorViewModel
import java.lang.IllegalArgumentException

class ContactCreatorViewModel(
    private val interactor: ContactsInteractor,
    private val mapper: ContactMapper,
) : BaseContactEditorViewModel<ContactState, FromContactCreator>(ContactState()) {

    private val logTag: String = this::class.java.simpleName

    fun init() {
        val contactId: String = interactor.getNewContactIndex()
        updateModel(contactId)
    }

    private fun updateModel(
        contactId: String? = model.contact?.id,
        personName: String? = model.contact?.name,
        personLastName: String? = model.contact?.lastName,
        personNumber: String? = model.contact?.number,
        personEmail: String? = model.contact?.email,
        imagePath: String? = model.contact?.imagePath,
        isNumberIndividual: Boolean? = model.isNumberIndividual,
    ) {
        if (contactId != null) {
            val contact = Contact(
                id = contactId,
                name = personName,
                lastName = personLastName,
                number = personNumber,
                email = personEmail,
                imagePath = imagePath
            )
            model = ContactState(contact, isNumberIndividual)
        } else {
            Log.e(logTag, "missing contact id", IllegalArgumentException())
        }
    }

    override fun onContactCorrect() {
        goToScreen(FromContactCreator.Command.AccessWritePermissions)
    }

    override fun onAddPhotoClick() {
        goToScreen(FromContactCreator.Command.AccessReadPermissions)
    }

    override fun onNameSubmit(name: String) =
        updateModel(personName = name)

    override fun onNumberSubmit(number: String) {
        val isNumberIndividual = interactor.isNumberIndividual(number)
        updateModel(personNumber = number, isNumberIndividual = isNumberIndividual)
    }


    override fun onReadStoragePermissionIsGranted() {
        goToScreen(FromContactCreator.Command.TakePictureFromGallery)
    }

    override fun onTakePicture(imageUri: Uri) {
        Log.v("IMAGE", imageUri.toString())
    }

    override fun onReadStoragePermissionIsNotGranted() {

    }

    override fun onWriteStoragePermissionIsGranted() {
        model.contact?.let { contact ->
            interactor.addContact(mapper.mapContact(contact))
        }
        goToPrevious()
    }

    override fun onWriteStoragePermissionIsNotGranted() {

    }

    override fun goToPrevious() =
        goToScreen(FromContactCreator.Navigate.PreviousScreen)

    override fun showMessageError() =
        goToScreen(FromContactCreator.Command.ShowErrorMessage(model.errorMessageResId))
}