package com.ariadna.taskieapp.templatemvvm.ui.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ariadna.taskieapp.templatemvvm.data.repository.login.LoginRepository
import com.ariadna.taskieapp.templatemvvm.ui.utils.FormValidations

class LoginViewModelFactory(
    private val formValidations: FormValidations,
    private val loginRepository: LoginRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(formValidations, loginRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}