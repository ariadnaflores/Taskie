package com.ariadna.taskieapp.templatemvvm.ui.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ariadna.taskieapp.templatemvvm.data.repository.TaskieRepository

class HomeViewModelFactory(private val taskieRepository: TaskieRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(taskieRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}