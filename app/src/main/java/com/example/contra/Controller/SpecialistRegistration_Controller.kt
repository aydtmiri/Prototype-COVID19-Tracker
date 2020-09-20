package com.example.contra.Controller

import com.example.contra.Data.SpecialistRegistration_Data
import com.example.contra.Model.FireBase_Callback
import com.example.contra.Model.Role
import com.example.contra.Model.Specialist_C

class SpecialistRegistration_Controller {

    lateinit var specialist: Specialist_C
    val data_layer = SpecialistRegistration_Data()

    /*  register specialist*/
    fun register_user(email: String, password: String, firstName: String, lastName: String,work_id:String,organization: String, country: String, firebaseCallback: FireBase_Callback) {

        specialist = Specialist_C(
            firstName,
            lastName,
            organization,
            work_id,
            country,
            Role.SPECIALIST
        )
        data_layer.register(email, password,specialist,firebaseCallback)

    }
}