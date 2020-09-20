package com.example.contra.View

import android.os.Build
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contra.Model.Activity
import com.example.contra.Model.Address
import com.example.contra.Model.Corona_Status
import com.example.contra.Model.FireBase_Callback
import com.example.contra.Controller.SpecialistAddCoronaCase_Controller
import com.example.contra.R
import kotlinx.android.synthetic.main.activity_specialist_add_corona_case.*


class SpecialistAddCoronaCaseActivity : AppCompatActivity(), ActivityDialog.ActivityDialogListener {

    lateinit var adapter : AdapterRecyclingView
    var activities : MutableList<Activity> = mutableListOf<Activity>()
    lateinit var recyclingView: RecyclerView
    val model = SpecialistAddCoronaCase_Controller()



    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialist_add_corona_case)

        //handleHideShowActivities()
        setSpinnerStyle()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun hideShowActivity(view:View){

        TransitionManager.beginDelayedTransition(ui_acs_activitiesView)

        if(ui_acs_activitiesView.visibility == View.GONE){

            ui_acs_hide_activites.setText(R.string.hideAktivity)
            ui_acs_activitiesView.visibility = View.VISIBLE
        }else
            if(ui_acs_activitiesView.visibility == View.VISIBLE) {

                ui_acs_hide_activites.setText(R.string.showAktivity)
                ui_acs_activitiesView.visibility = View.GONE
            }

    }

    fun addCoronaCase(view: View){

        checkForEmptyFields()

        //------Address---------
        val street: String = input_acs_street.text.toString()
        val plz: Int = input_acs_plz.text.toString().toInt()
        val city : String = input_acs_city.text.toString()
        val country : String = input_acs_country.selectedItem.toString()

        var address =
            Address(street, plz, city, country)

        //------ other input -------

        val patientID: String = input_acs_patient_id.text.toString()
        val caseDate: String = input_acs_date.text.toString()
        val coronaStatusString :String = input_acs_corona_status.selectedItem.toString()
        val coronarRadius : String = input_acs_radius.selectedItem.toString()



        var coronaStatus: Corona_Status

        coronaStatus = when (coronaStatusString) {
            "Positiv" -> Corona_Status.POSITIVE
            "Verdacht" -> Corona_Status.SUSPECTED
            else -> {
                Corona_Status.POSITIVE
            }
        }

        model.addCoronaCase(patientID,caseDate,address,coronaStatus,this,coronarRadius.toInt(),activities, object :
            FireBase_Callback {
            override fun finishedTask() {
                Toast.makeText(this@SpecialistAddCoronaCaseActivity,"erfolgreich", Toast.LENGTH_LONG).show()
            }

            override fun taskFailed(e: Exception) {
                Toast.makeText(this@SpecialistAddCoronaCaseActivity,"Adden ist fehlgeschlagen", Toast.LENGTH_LONG).show()
            }

        })


    }

     fun addActivity(view: View){
        openDialog()
    }

    private fun openDialog(){
        val activityDialog = ActivityDialog()
        activityDialog.show(supportFragmentManager,"test")
    }

    private fun setSpinnerStyle() {

        //----------STATUS DROPDOWN -----------------

        var adapter_status = ArrayAdapter.createFromResource(
            this,
            R.array.Status,
            R.layout.spinner_layout_white
        )
        adapter_status.setDropDownViewResource(R.layout.spinner_dropdown_layout_white)

        var statusDropdown = findViewById<Spinner>(R.id.input_acs_corona_status)
        statusDropdown.adapter = adapter_status

        //----------RADIUS DROPDOWN -----------------

        var adapter_radius = ArrayAdapter.createFromResource(
            this,
            R.array.Radius,
            R.layout.spinner_layout_white
        )
        adapter_radius.setDropDownViewResource(R.layout.spinner_dropdown_layout_white)

        var radiusDropdown = findViewById<Spinner>(R.id.input_acs_radius)
        radiusDropdown.adapter = adapter_radius

        //----------COUNTRY DROPDOWN -----------------

        var adapter_country = ArrayAdapter.createFromResource(
            this,
            R.array.Länder,
            R.layout.spinner_layout_white
        )
        adapter_country.setDropDownViewResource(R.layout.spinner_dropdown_layout_white)

        var countryDropdown = findViewById<Spinner>(R.id.input_acs_country)
        countryDropdown.adapter = adapter_country

    }

    override fun applyEntries(editTitle: String, editDate: String, editTime: String, editLocation: String,editDescription: String) {

        var activity =
            Activity(
                editTitle,
                editDate,
                editTime,
                editLocation,
                editDescription
            )

        activities.add(activity)

        recyclingView = ui_acs_activitiesView
        recyclingView.layoutManager = LinearLayoutManager(this)
        adapter = AdapterRecyclingView(this, activities)
        recyclingView.adapter = adapter

        adapter.setOnItemClickListener(object : AdapterRecyclingView.OnItemClickListener {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onItemClick(position: Int) {

                var details =
                    ui_acs_activitiesView[position].findViewById<ConstraintLayout>(R.id.activityDetailsView)
                var showHideText =
                    ui_acs_activitiesView[position].findViewById<TextView>(R.id.activityShowHideDetail)


                val autoTransition = AutoTransition()
                autoTransition.setDuration(100)

                TransitionManager.beginDelayedTransition(
                    ui_acs_activitiesView[position] as ViewGroup?,
                    autoTransition
                )

                if (details.visibility == View.VISIBLE) {

                    details.visibility = View.GONE
                    showHideText.setText(R.string.moreDetails)
                } else
                    if (details.visibility == View.GONE) {
                        details.visibility = View.VISIBLE
                        showHideText.setText(R.string.lessDetails)
                    }


            }

        })


    }

    fun checkForEmptyFields(){

        evaluateTextFieldisEmpty(input_acs_patient_id)
        evaluateTextFieldisEmpty(input_acs_street)
        evaluateTextFieldisEmpty(input_acs_city)
        evaluateTextFieldisEmpty(input_acs_plz)
        evaluateTextFieldisEmpty(input_acs_date)

        if(input_acs_corona_status.toString().equals("Status auswählen")){
            (input_acs_corona_status.selectedView as TextView).error = "Bitte wählen Sie ein Status aus"
        }



    }

    private fun evaluateTextFieldisEmpty( ui_element: TextView){
        if((ui_element.text.toString().equals(null)) or (ui_element.text.toString().equals(""))) {
            ui_element.error ="Bitte füllen Sie das Feld aus."
            ui_element.requestFocus()

        }
    }



}