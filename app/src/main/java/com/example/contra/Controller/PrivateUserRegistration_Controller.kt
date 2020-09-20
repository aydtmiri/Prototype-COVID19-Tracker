package com.example.contra.Controller

import android.content.Context
import com.example.contra.Data.PrivateUserRegistration_Data
import com.example.contra.Model.Address
import com.example.contra.Model.FireBase_Callback
import com.example.contra.Model.PrivateUserC
import com.example.contra.Model.Role

class PrivateUserRegistration_Controller {

    lateinit var private_user: PrivateUserC
    val data_layer = PrivateUserRegistration_Data()

    /*  register user with email address and password*/
    fun register_user(email: String, password: String, context: Context, _address: Address, firebaseCallback: FireBase_Callback) {

        //create new private user objects
        private_user = PrivateUserC(
            Role.PRIVATE_USER,
            _address,
            context
        )

        //save new user object in database
        data_layer.register(email, password,private_user,firebaseCallback)

    }

}