package com.ariadna.taskieapp.templatemvvm.ui.utils

import androidx.core.util.PatternsCompat

class FormValidations {

    private var userEmailState = EmailValidationState.EmailNotValid

    private var userEmail = ""


    fun setFormFields(email: String, password: String) {
        this.userEmail = email
        //TODO: do the same with the password field
    }

    fun validateEmail(): EmailValidationState {
        return when {
            userEmail.isEmpty() -> {
                userEmailState = EmailValidationState.EmptyEmail
                EmailValidationState.EmptyEmail
            }
            PatternsCompat.EMAIL_ADDRESS.matcher(userEmail).matches() -> {
                userEmailState = EmailValidationState.ValidEmail
                EmailValidationState.ValidEmail
            }
            else -> {
                userEmailState = EmailValidationState.EmailNotValid
                EmailValidationState.EmailNotValid
            }
        }
    }

    private fun isEmailValid(): Boolean {
        return userEmailState == EmailValidationState.ValidEmail
    }



    fun isFormValid(): Boolean {
        return isEmailValid()
    }
}

enum class EmailValidationState {
    EmptyEmail, EmailNotValid, ValidEmail,
}