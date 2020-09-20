package com.example.contra.Controller

import com.example.contra.Data.Login_Data
import com.example.contra.Model.Login_Callback

class Login_Controller {

    val login_data = Login_Data()

    /* Login with email and password */
    fun login(_email:String, _password:String, callback: Login_Callback){

        login_data.logIn(_email,_password,callback)

    }

}