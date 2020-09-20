package com.example.contra.Model

import android.content.Context
import com.example.contra.Controller.GetLocationFromAdress_Helper
import java.lang.Exception


class PrivateUserC(_role: Role, _address: Address, _context:Context) : GeneralUser_C(_role) {


     var corona_status: Corona_Status
     var location: Location
     var coronaNotification: MutableMap<String, CoronaNotification>? = null



    init {
        corona_status = Corona_Status.NEGATIVE
        location = setLocation(_context, _address )
        role = _role
    }

    fun addNotification(key: String, notification: CoronaNotification) {

        if (coronaNotification?.containsKey(key)!!)
            throw Exception("You already added this activity.")
        else
            coronaNotification?.put(key, notification)

    }

    fun removeActivity(key: String, notification: CoronaNotification) {
        if ((coronaNotification?.size != 0) or (coronaNotification != null)) {
            if (coronaNotification?.containsKey(key)!!)
                coronaNotification!!.remove(key)
        }
    }

    fun updateActivity(key: String, notification: CoronaNotification) {
        if ((coronaNotification?.size != 0) or (coronaNotification != null)) {
            coronaNotification?.put(key, notification)


        }

    }

    fun setLocation(context: Context, address: Address): Location {
        return GetLocationFromAdress_Helper.getLocationFromAddress(context,address)
    }


}
