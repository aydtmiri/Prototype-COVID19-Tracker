package com.example.contra.Model

import android.content.Context
import com.example.contra.Controller.GetLocationFromAdress_Helper
import java.io.Serializable

class CoronaCase() : Serializable {

    lateinit var patient_id: String
    lateinit var case_date: String
    lateinit var patient_location: Location
    lateinit var corona_status: Corona_Status
    var activities: MutableList<Activity> = mutableListOf<Activity>()

   /* fun addActivity(key: String, activity: Activity) {

        if (activities?.containsKey(key)!!)
            throw Exception("You already added this activity.")
        else
            activities?.put(key, activity)

    }

    fun removeActivity(key: String, activity: Activity) {
        if ((activities?.size != 0) or (activities != null)) {
            if (activities?.containsKey(key)!!)
                activities!!.remove(key)
        }
    }

    fun updateActivity(key: String, activity: Activity) {
        if ((activities?.size != 0) or (activities != null)) {
            activities?.put(key, activity)


        }

   }*/

    private fun setLocation(context: Context, address: Address): Location {

        return  GetLocationFromAdress_Helper.getLocationFromAddress(context,address)
    }
    fun setCoronaCase(_patientId: String, _caseDate: String, _address: Address, _coronaStatus: Corona_Status, _activities:MutableList<Activity>, _context: Context){
        patient_id = _patientId
        case_date = _caseDate
        patient_location = _context?.let { setLocation(it, _address) }!!
        corona_status = _coronaStatus
        activities = _activities
    }

}

