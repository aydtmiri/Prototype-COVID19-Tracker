package com.example.contra.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import com.example.contra.Model.FireBase_Callback
import com.example.contra.Controller.SpecialistRegistration_Controller
import com.example.contra.R
import kotlinx.android.synthetic.main.activity_register_specialist.*

class RegisterSpecialistActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener  {
    val model = SpecialistRegistration_Controller()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_register_specialist)

        setSpinnerStyle()

        s_ui_progressBar.visibility = View.GONE

    }

    fun  registerSpecialistAction(view: View) {

        s_ui_progressBar.visibility = View.VISIBLE


        try {
            //---------------------------------------evaluate if input is empty ---------------------------------------//
            evaluateUserInput()
            //--------------------------------------- get input from GUI ---------------------------------------//
            val firstName: String = s_input_firstName.text.toString()
            val lastName: String = s_input_lastName.text.toString()
            val country : String = s_input_country.selectedItem.toString()
            val organization : String = input_workplace.text.toString()
            val workId : String = s_input_work_id.text.toString()
            val email: String = s_input_email.text.toString()
            val password: String = s_input_password.text.toString()



            model.register_user(email, password,firstName,lastName,workId,organization,country,object:
                FireBase_Callback {
                override fun finishedTask() {

                    Log.d("MAIN", "Email: ${email}")
                    Log.d("MAIN", "Password: ${password}")

                    openNewActivity()
                }

                override fun taskFailed(e:Exception) {
                    s_ui_progressBar.visibility = View.GONE
                    Toast.makeText(this@RegisterSpecialistActivity,"Registrierung ist fehlgeschlagen",Toast.LENGTH_LONG).show()
                }
            })


        }catch (e:Exception){

            Log.d("ERROR",e.toString())


        }


    }
    private fun openNewActivity(){

        var intent= Intent(this, RegistrationSuccssesfullActivity::class.java)
        startActivity(intent)

    }

    private fun evaluateUserInput()
    {
        try {
            evaluateTextFieldisEmpty(s_input_firstName)
            evaluateTextFieldisEmpty(s_input_lastName)
            evaluateTextFieldisEmpty(input_workplace)
            evaluateTextFieldisEmpty(s_input_work_id)
            evaluateTextFieldisEmpty(s_input_email)
            evaluateTextFieldisEmpty(s_input_password)

            if(!Patterns.EMAIL_ADDRESS.matcher(s_input_email.text.toString()).matches())
            {
                s_input_email.error ="Bitte geben sie eine gültige Email ein."
                s_input_email.requestFocus()
            }

            if(s_input_password.text.toString().count()<=5){
                s_input_password.error ="Das Passwort muss mindestens 6 Zeichen lang sein."
                s_input_password.requestFocus()
            }
        }catch (e:Exception){
            Log.d("EMPTY_USER_INPUT","Empty user input.")

        }

    }

    private fun evaluateTextFieldisEmpty( ui_element: TextView){
        if((ui_element.text.toString().equals(null)) or (ui_element.text.toString().equals(""))) {
            ui_element.error ="Bitte füllen Sie das Feld aus."
            ui_element.requestFocus()

        }

    }

    private fun setSpinnerStyle() {

        var adapter = ArrayAdapter.createFromResource(
            this,
            R.array.Länder,
            R.layout.spinner_layout_lila
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout_lila)

        var countryDropdown = findViewById<Spinner>(R.id.s_input_country)
        countryDropdown.adapter = adapter

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }
}
