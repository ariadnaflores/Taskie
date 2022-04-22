package com.ariadna.taskieapp.templatemvvm.data.repository.data

class UserDataNotes (
    var titleNote : String,
    var noteContent : String,
    var priorityColor: PriorityColor
        )

enum class PriorityColor  {
    HIGH,
    MEDIUM,
    LOW
}