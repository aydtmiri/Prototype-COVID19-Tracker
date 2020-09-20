package com.example.contra.Model

import java.lang.Exception

interface FireBaseRole_Callback {

    fun retrievedRole(role: Role)
    fun failedToRetrieveRole(e: Exception)
}