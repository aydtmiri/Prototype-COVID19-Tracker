package com.example.contra.Model

import java.lang.Exception

interface Login_Callback {

    fun login_successul()
    fun login_failed(exception: Exception)
}