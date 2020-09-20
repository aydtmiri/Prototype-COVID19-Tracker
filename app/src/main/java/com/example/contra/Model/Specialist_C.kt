package com.example.contra.Model

import com.example.contra.Model.CoronaNotification
import com.example.contra.Model.GeneralUser_C
import com.example.contra.Model.Role
import java.lang.Exception

class Specialist_C(_firstName:String, _lastName:String, _organization:String, _work_id:String, _country:String, _role: Role):
    GeneralUser_C( _role ) {

    var organization: String
    var workId: String
    var country: String
    var firstName: String
    var lastName: String
    var coronaNotification: MutableMap<String, CoronaNotification>? = null

    init {
        organization = _organization
        workId =_work_id
        country = _country
        firstName = _firstName
        lastName = _lastName

    }
    fun addCoronaNotification(key: String, _coronaNotification: CoronaNotification) {

        if (coronaNotification?.containsKey(key)!!)
            throw Exception("You already added this activity.")
        else
            coronaNotification?.put(key, _coronaNotification)

    }

    fun removeCoronaNotification(key: String, _coronaNotification: CoronaNotification) {
        if ((coronaNotification?.size != 0) or (coronaNotification != null)) {
            if (coronaNotification?.containsKey(key)!!)
                coronaNotification!!.remove(key)
        }
    }

    fun updateCoronaNotifications(key: String, _coronaNotification: CoronaNotification) {
        if ((coronaNotification?.size != 0) or (coronaNotification != null)) {
            coronaNotification?.put(key, _coronaNotification)


        }

    }

}
