package com.ariadna.taskieapp.templatemvvm.ui.login.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ariadna.taskieapp.templatemvvm.ui.utils.EmailValidationState
import com.ariadna.taskieapp.templatemvvm.ui.utils.FormValidations

class LoginViewModel(
    private val formValidator: FormValidations
) : ViewModel() {

    private val loginViewStateMutableLiveData = MutableLiveData<LoginViewState>()
    val loginViewStateLiveData: LiveData<LoginViewState>
        get() = loginViewStateMutableLiveData

    fun checkFields(userEmail: String, userPassword: String) {
        formValidator.setFormFields(email = userEmail, password = userPassword)
        checkEmail()

        if (formValidator.isFormValid()) {
            loginViewStateMutableLiveData.postValue(UserLoggedIn)
        }
    }

    private fun checkEmail() {
        when (formValidator.validateEmail()) {
             EmailValidationState.EmptyEmail -> {
                 loginViewStateMutableLiveData.postValue(UserEmptyEmail)
             }
            EmailValidationState.EmailNotValid -> {
                loginViewStateMutableLiveData.postValue(UserInvalidEmail)
            }
            EmailValidationState.ValidEmail -> {
                loginViewStateMutableLiveData.postValue(UserValidEmail)
            }
        }
    }
}

sealed class LoginViewState

object UserLoggedIn : LoginViewState()

object UserInvalidEmail : LoginViewState()
object UserEmptyEmail : LoginViewState()
object UserValidEmail: LoginViewState()

//TODO: map states for password field



