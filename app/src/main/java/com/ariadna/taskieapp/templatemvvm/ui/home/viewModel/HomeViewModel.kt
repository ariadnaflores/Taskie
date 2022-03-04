package com.ariadna.taskieapp.templatemvvm.ui.home.viewModel

import androidx.lifecycle.ViewModel
import com.ariadna.taskieapp.templatemvvm.data.repository.TaskieRepository


class HomeViewModel (
    private val taskieRepository: TaskieRepository
): ViewModel() {

    fun validateIsUserLoggedIn(): Boolean  {
        return taskieRepository.getIsUserLoggedIn()
    }

    fun deleteData(){
        taskieRepository.wipe()
    }

    fun getEmail(): String {
        return taskieRepository.getUserEmail()
    }
}