package com.example.contra.Controller

import android.content.Context
import com.example.contra.Data.GeneralData_Data
import com.example.contra.Data.SpecialistAddCoronaCase_Data
import com.example.contra.Model.*
import java.text.SimpleDateFormat
import java.util.*

class SpecialistAddCoronaCase_Controller {

    lateinit var specialist: Specialist_C
    val data_layer = SpecialistAddCoronaCase_Data()
    val generalUser_data = GeneralData_Data()
    var coronaCase = CoronaCase()

    /*  Add new covid 19 case */
    fun addCoronaCase(_patientId: String, _caseDate: String, _address: Address, _coronaStatus: Corona_Status, _context: Context, _notification_radius:Int, _activities: MutableList<Activity>, firebaseCallback: FireBase_Callback){

        //---------------------------------------get current user id ---------------------------------------//
        val reporterId = generalUser_data.getCurrentUserId()

        //---------------------------------------create new corona case ------------------------------------//
        coronaCase.setCoronaCase(_patientId,_caseDate,_address,_coronaStatus,_activities ,_context)



        //---------------------------------------create new corona notification ------------------------------------//
        val time = Calendar.getInstance().time
        val df = SimpleDateFormat("dd.MMM.yyyy")
        val formattedDate = df.format(time)

        val coronaNotification  = CoronaNotification(
            coronaCase,
            formattedDate,
            time.toString(),
            _notification_radius,
            reporterId
        )

        //--------------------------------------- save and notify all nec users ------------------------------------//

        data_layer.registerCoronaNotification(coronaNotification,firebaseCallback)



    }



}