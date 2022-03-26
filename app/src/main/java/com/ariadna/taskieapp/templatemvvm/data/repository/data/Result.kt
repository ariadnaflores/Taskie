package com.ariadna.taskieapp.templatemvvm.data.repository.data

class Result {

}

sealed class ResultState
data class Successful(val data : Any) : ResultState()
data class Failed(val error : String) : ResultState()