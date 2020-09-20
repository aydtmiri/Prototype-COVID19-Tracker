package com.example.contra.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.contra.R

class RegistrationSuccssesfullActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_succssesfull)
    }

    public fun backToLogIn(view: View)
    {
        var intent= Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }
}
