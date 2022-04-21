package com.ariadna.taskieapp.templatemvvm.ui.utils

import androidx.core.util.PatternsCompat

class FormValidations {

    private var userEmailState = EmailValidationState.EmailNotValid

    private var userEmail = ""

    private var passwordState = PasswordValidationState.InvalidPasswordLength

    private var password = ""


    fun setFormFields(email: String, password: String) {
        this.userEmail = email
        this.password = password
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

    fun validatePassword(): PasswordValidationState{

        val passwordLength = password.length >4

        return when {
            password.isEmpty() -> {
                passwordState = PasswordValidationState.EmptyPassword
                PasswordValidationState.EmptyPassword
            }
            passwordLength -> {
                passwordState = PasswordValidationState.ValidPassword
                PasswordValidationState.ValidPassword
            }
            else -> {
                passwordState = PasswordValidationState.InvalidPasswordLength
                PasswordValidationState.InvalidPasswordLength
            }
        }
    }

    private fun isPasswordValid(): Boolean {
        return passwordState == PasswordValidationState.ValidPassword
    }

    fun isFormValid(): Boolean {
        return isEmailValid() && isPasswordValid()
    }

}

enum class EmailValidationState {
    EmptyEmail, EmailNotValid, ValidEmail,
}

enum class PasswordValidationState {
    EmptyPassword, InvalidPasswordLength, ValidPassword,
}
