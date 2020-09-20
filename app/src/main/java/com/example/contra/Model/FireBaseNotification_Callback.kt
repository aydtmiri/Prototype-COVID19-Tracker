package com.example.contra.Model

import com.example.contra.Model.CoronaNotification
import java.lang.Exception

interface FireBaseNotification_Callback {

    fun finishedRetrieveNotification(_notifications: MutableList<CoronaNotification>)
    fun taskFailed(e: Exception)
}