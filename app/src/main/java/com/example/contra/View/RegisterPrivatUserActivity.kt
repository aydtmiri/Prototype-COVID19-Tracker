package com.example.contra.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.contra.Model.Address
import com.example.contra.Model.FireBase_Callback
import com.example.contra.Controller.PrivateUserRegistration_Controller
import com.example.contra.R
import kotlinx.android.synthetic.main.activity_register_private_user.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class RegisterPrivatUserActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener ,
    FireBase_Callback {

    val model = PrivateUserRegistration_Controller()
    var job : Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register_private_user)

        //set style of dropdown
        setSpinnerStyle()
        pu_progressBar.visibility = View.GONE



    }

     fun  registerUserAction(view: View) {

         val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
         inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0)


         pu_progressBar.visibility = View.VISIBLE


        try {
            //---------------------------------------evaluate if input is empty ---------------------------------------//
             evaluateUserInput()
            //--------------------------------------- get input from GUI ---------------------------------------//
            val street: String = pu_input_streetName.text.toString()
            val plz: Int = pu_input_plz.text.toString().toInt()
            val city : String = pu_input_town.text.toString()
            val country : String = pu_input_country.toString()
            val email: String = pu_input_email.text.toString()
            val password: String = pu_input_password.text.toString()

           val address =
               Address(street, plz, city, country)

            GlobalScope.launch { // launch a new coroutine and keep a reference to its Job

                model.register_user(email, password, applicationContext,address,object:
                    FireBase_Callback {
                    override fun finishedTask() {

                       // Log.d("MAIN", "Email: ${email}")
                        //Log.d("MAIN", "Password: ${password}")
                        constraint_layout_pu.visibility = View.GONE
                        openNewActivity()


                    }

                    override fun taskFailed(e: java.lang.Exception) {
                        Toast.makeText(this@RegisterPrivatUserActivity,"Registrierung ist fehlgeschlagen",Toast.LENGTH_LONG).show()
                        pu_progressBar.visibility = View.GONE
                    }
                })

           }

        }catch (e:Exception){

            Log.d("ERROR",e.toString())


        }



}


    private fun openNewActivity(){

        var intent= Intent(this, RegistrationSuccssesfullActivity::class.java)
        startActivity(intent)

    }


    private fun setSpinnerStyle() {

        var adapter = ArrayAdapter.createFromResource(
            this,
            R.array.Länder,
            R.layout.spinner_layout_lila
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout_lila)

        var countryDropdown = findViewById<Spinner>(R.id.pu_input_country)
        countryDropdown.adapter = adapter

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    private fun evaluateUserInput()
    {

            evaluateTextFieldisEmpty(pu_input_streetName)
            evaluateTextFieldisEmpty(pu_input_plz)
            evaluateTextFieldisEmpty(pu_input_town)
            evaluateTextFieldisEmpty(pu_input_email)
            evaluateTextFieldisEmpty(pu_input_password)

            if(!Patterns.EMAIL_ADDRESS.matcher(pu_input_email.text.toString()).matches())
            {
                pu_progressBar.visibility = View.GONE
                pu_input_email.error ="Bitte geben sie eine gültige Email ein."
                pu_input_email.requestFocus()
            }

            if(pu_input_password.text.toString().count()<=5){
                pu_progressBar.visibility = View.GONE
                pu_input_password.error ="Das Passwort muss mindestens 6 Zeichen lang sein."
                pu_input_password.requestFocus()
            }
    }

    private fun evaluateTextFieldisEmpty( ui_element: TextView){
        if((ui_element.text.toString().equals(null)) or (ui_element.text.toString().equals(""))) {
            pu_progressBar.visibility = View.GONE
            ui_element.error ="Bitte füllen Sie das Feld aus."
            ui_element.requestFocus()

        }

    }

    override fun finishedTask() {
        Log.d("gfg","xgfdg")
    }

    override fun taskFailed(e: java.lang.Exception) {
        Log.d("gfg","xgfdg")
    }




}
