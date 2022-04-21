package com.ariadna.taskieapp.templatemvvm.ui.home.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ariadna.taskieapp.templatemvvm.data.repository.TaskieRepository
import com.ariadna.taskieapp.templatemvvm.data.repository.home.HomeRepository


class HomeViewModel (
    private val taskieRepository: TaskieRepository,
    private val homeRepository: HomeRepository
): ViewModel() {

    private val homeViewStateMutableLiveData = MutableLiveData<HomeViewState>()
    val homeViewStateLiveData: LiveData<HomeViewState>
        get() = homeViewStateMutableLiveData

     fun validateIsUserLoggedIn(): Boolean  {
        return taskieRepository.getIsUserLoggedIn()
    }

    fun deleteData(){
        taskieRepository.wipe()
    }

    fun getEmail(): String {
        return taskieRepository.getUserEmail()
    }

    fun logOut() {
        homeRepository.logOut()
            Log.e("From Home Repository", "User log out")
            homeViewStateMutableLiveData.postValue(UserLogOut)
    }
}

sealed class  HomeViewState
object UserLogOut : HomeViewState()


