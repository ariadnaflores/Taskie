package com.ariadna.taskieapp.templatemvvm.ui.register.viewmodel

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ariadna.taskieapp.templatemvvm.data.repository.register.RegisterRepository
import com.ariadna.taskieapp.templatemvvm.ui.utils.EmailValidationState
import com.ariadna.taskieapp.templatemvvm.ui.utils.FormValidations
import com.ariadna.taskieapp.templatemvvm.ui.utils.PasswordValidationState

class RegisterViewModel(
    private val formValidator: FormValidations,
    private val registerRepository: RegisterRepository
    )
: ViewModel() {
    private val registerViewStateMutableLiveData = MutableLiveData<RegisterViewState>()
    val registerViewStateLiveData: LiveData<RegisterViewState>
        get() = registerViewStateMutableLiveData

    fun checkFields(userEmail: String, userPassword: String) {
        formValidator.setFormFields(email = userEmail, password = userPassword)
        checkEmail()
        checkPassword()

        if (formValidator.isFormValid()) {
            registerViewStateMutableLiveData.postValue(UserLoggedIn)
        }
    }

    private fun checkEmail() {
        when (formValidator.validateEmail()) {
            EmailValidationState.EmptyEmail -> {
                registerViewStateMutableLiveData.postValue(UserEmptyEmail)
            }
            EmailValidationState.ValidEmail -> {
                registerViewStateMutableLiveData.postValue(UserValidEmail)
            }
            EmailValidationState.EmailNotValid -> {
                registerViewStateMutableLiveData.postValue(UserInvalidEmail)
            }
        }
    }

    private fun checkPassword() {
        when (formValidator.validatePassword()) {
            PasswordValidationState.EmptyPassword -> {
                registerViewStateMutableLiveData.postValue(UserEmptyPassword)
            }
            PasswordValidationState.InvalidPasswordLength -> {
                registerViewStateMutableLiveData.postValue(UserInvalidPasswordLength)
            }
            PasswordValidationState.ValidPassword -> {
                registerViewStateMutableLiveData.postValue(UserValidPassword)
            }
        }
    }

    fun createUser(
        userEmail: String,
        userPassword: String,
        activity: AppCompatActivity,
    ) {
           registerRepository.createUser(userEmail, userPassword, activity, onSuccess = {
               Log.e("From Register Viewmodel success", "------------")
               registerViewStateMutableLiveData.postValue(UserSuccessfullyCreatedInFirebase)
           },
               onFailed = {
                   Log.e("From Register Viewmodel failed", "User failed")
                   registerViewStateMutableLiveData.postValue(UnsuccessfulUserInFirebase)
               })
    }
}

sealed class RegisterViewState

object UserLoggedIn : RegisterViewState()
object UserEmptyEmail : RegisterViewState()
object UserValidEmail: RegisterViewState()
object UserInvalidEmail : RegisterViewState()
object UserEmptyPassword : RegisterViewState()
object UserInvalidPasswordLength : RegisterViewState()
object UserValidPassword : RegisterViewState()

object UserSuccessfullyCreatedInFirebase : RegisterViewState()
object UnsuccessfulUserInFirebase : RegisterViewState()

