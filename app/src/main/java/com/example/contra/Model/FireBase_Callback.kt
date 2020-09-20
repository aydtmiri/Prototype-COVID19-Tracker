package com.example.contra.Model

import java.lang.Exception

interface FireBase_Callback {

    fun finishedTask()
    fun taskFailed(e: Exception)
}