package com.example.contra.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.contra.R

class ChooseRoleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_role)
    }

    fun registerPrivatePerson(view: View)
    {
        var intent= Intent(this, RegisterPrivatUserActivity::class.java)
        startActivity(intent)

    }

    fun registerSpecialist(view:View)
    {
        var intent= Intent(this, RegisterSpecialistActivity::class.java)
        startActivity(intent)
    }


}
