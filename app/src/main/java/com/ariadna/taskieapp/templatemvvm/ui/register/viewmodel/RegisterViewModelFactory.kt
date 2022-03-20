package com.ariadna.taskieapp.templatemvvm.ui.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ariadna.taskieapp.templatemvvm.data.repository.register.RegisterRepository
import com.ariadna.taskieapp.templatemvvm.ui.utils.FormValidations

class RegisterViewModelFactory (
    private val formValidations: FormValidations,
    private val registerRepository: RegisterRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            RegisterViewModel(formValidations, registerRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}