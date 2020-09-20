package com.example.contra.Controller

import com.example.contra.Data.GetCoronaNotification_Data
import com.example.contra.Model.FireBaseNotification_Callback
import com.example.contra.Model.FireBase_Callback

class OverviewPrivateUser_Controller {

    var data = GetCoronaNotification_Data()

    /*  Get all available covid 19 notification of current user*/
     fun getCoronaNotifications(firebaseCallback: FireBase_Callback, firebasenotificationCallback: FireBaseNotification_Callback){

        data.getCoronaNotificationData(firebaseCallback,firebasenotificationCallback)
    }
}