package com.example.contra.Data

import android.util.Log
import com.example.contra.Model.Login_Callback
import com.google.firebase.auth.FirebaseAuth

class Login_Data {


    fun logIn(email:String, password: String, callback: Login_Callback){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnSuccessListener{
                Log.d("User_Login","Successfully logged in user with uid: ${it.user?.uid}")
                callback.login_successul()
            }
            .addOnFailureListener {
                callback.login_failed(it)
            }
    }

}