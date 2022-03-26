package com.ariadna.taskieapp.templatemvvm.ui.login.viewModel

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ariadna.taskieapp.templatemvvm.data.repository.login.LoginRepository
import com.ariadna.taskieapp.templatemvvm.ui.utils.EmailValidationState
import com.ariadna.taskieapp.templatemvvm.ui.utils.FormValidations
import com.ariadna.taskieapp.templatemvvm.ui.utils.PasswordValidationState

class LoginViewModel(
    private val formValidator: FormValidations,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val loginViewStateMutableLiveData = MutableLiveData<LoginViewState>()
    val loginViewStateLiveData: LiveData<LoginViewState>
        get() = loginViewStateMutableLiveData

    fun checkFields(userEmail: String, userPassword: String) {
        formValidator.setFormFields(email = userEmail, password = userPassword)
        checkEmail()
        checkPassword()

        if (formValidator.isFormValid()) {
            loginViewStateMutableLiveData.postValue(UserLoggedIn)
        }
    }

    private fun checkEmail() {
        when (formValidator.validateEmail()) {
            EmailValidationState.EmptyEmail -> {
                loginViewStateMutableLiveData.postValue(UserEmptyEmail)
            }
            EmailValidationState.ValidEmail -> {
                loginViewStateMutableLiveData.postValue(UserValidEmail)
            }
            EmailValidationState.EmailNotValid -> {
                loginViewStateMutableLiveData.postValue(UserInvalidEmail)
            }
        }
    }

    private fun checkPassword() {
        when (formValidator.validatePassword()) {
            PasswordValidationState.EmptyPassword -> {
                loginViewStateMutableLiveData.postValue(UserEmptyPassword)
            }
            PasswordValidationState.InvalidPasswordLength -> {
                loginViewStateMutableLiveData.postValue(UserInvalidPasswordLength)
            }
            PasswordValidationState.ValidPassword -> {
                loginViewStateMutableLiveData.postValue(UserValidPassword)
            }
        }
    }

    fun signIn(
        userEmail: String,
        userPassword: String,
        activity: AppCompatActivity
    ) {
        loginRepository.signIn(userEmail, userPassword, activity, onSuccess = {
            Log.e("From Viewmodel success", "------------")
            loginViewStateMutableLiveData.postValue(FirebaseSuccessful)
        },
            onFailed = {
                Log.e("From Viewmodel failed", "User already login")
                loginViewStateMutableLiveData.postValue(FirebaseFailed(error = it))
            })
    }
}

sealed class LoginViewState

object UserLoggedIn : LoginViewState()

object UserEmptyEmail : LoginViewState()
object UserValidEmail : LoginViewState()
object UserInvalidEmail : LoginViewState()
object UserEmptyPassword : LoginViewState()
object UserInvalidPasswordLength : LoginViewState()
object UserValidPassword : LoginViewState()

object FirebaseSuccessful : LoginViewState()
data class FirebaseFailed(val error : String) : LoginViewState()



