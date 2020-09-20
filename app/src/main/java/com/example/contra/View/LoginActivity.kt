package com.example.contra.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.contra.Data.GeneralData_Data
import com.example.contra.Model.FireBaseRole_Callback
import com.example.contra.Model.Login_Callback
import com.example.contra.Model.Role
import com.example.contra.Controller.Login_Controller
import com.example.contra.R
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.Exception


class LoginActivity : AppCompatActivity() {

    val model = Login_Controller()
    val general_data = GeneralData_Data()
    var intentSpecialist = Intent()
    var intentPrivatUser = Intent()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        intentSpecialist = Intent(this, SpecialistAddCoronaCaseActivity::class.java)
        intentPrivatUser = Intent(this, OverviewPrivateUserActivity::class.java)
    }

    
    open fun changeToChooseRole(view: View) {
        var intent = Intent(this, ChooseRoleActivity::class.java)
        startActivity(intent)

    }

    open fun logIn(view: View) {


        var email: String = ui_input_email.text.toString()
        var password: String = ui_input_password.text.toString()

                model.login(email,password, object:
                    Login_Callback {
                    override fun login_successul() {
                        var role = general_data.getUserRole(object:
                            FireBaseRole_Callback {
                            override fun retrievedRole(role: Role) {



                                if(role == Role.SPECIALIST){
                                    startActivity(intentSpecialist)
                                }

                                if(role == Role.PRIVATE_USER){
                                    startActivity(intentPrivatUser)
                                }
                            }

                            override fun failedToRetrieveRole(e: Exception) {

                            }

                        })

                    }

                    override fun login_failed(exception: Exception) {
                        Toast.makeText(this@LoginActivity,"Der Benutzername oder das Passwort ist falsch.",
                            Toast.LENGTH_LONG).show()
                    }

                })


    }


}






