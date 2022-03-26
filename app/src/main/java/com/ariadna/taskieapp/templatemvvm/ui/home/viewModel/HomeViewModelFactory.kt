package com.ariadna.taskieapp.templatemvvm.ui.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ariadna.taskieapp.templatemvvm.data.repository.TaskieRepository
import com.ariadna.taskieapp.templatemvvm.data.repository.home.HomeRepository

class HomeViewModelFactory(
    private val taskieRepository: TaskieRepository,
    private val homeRepository: HomeRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(taskieRepository, homeRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}