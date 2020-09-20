package com.example.contra.Data

import android.util.Log
import com.example.contra.Model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList


class GetCoronaNotification_Data {

    val TAG = "GET_CORONA_NOTIFICATION"
    val generalData = GeneralData_Data()
    val notifications = mutableListOf<CoronaNotification>()
    val dB = Firebase.firestore
    val activities = mutableListOf<Activity>()
    var number_of_notifications = 0



     fun getCoronaNotificationData(
         firebaseCallback: FireBase_Callback,
         notificationCallback: FireBaseNotification_Callback
    ) {

        Log.d(TAG, "--------start of function that get COVID-19 Notification -----------------")

        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid != null) {

            //----------init dB variables ---------------//


            val notificationRef = dB.collection("corona_notification")

            Log.d(TAG, "------Role of current user ------")

            generalData.getUserRole(object :
                FireBaseRole_Callback {
                override fun retrievedRole(role: Role) {


                    notificationRef
                        .whereArrayContains("affectedUser", uid)
                        .get()
                        .addOnSuccessListener {

                            number_of_notifications = it.documents.size

                            for (notificationDoc in it.documents) {

                                getNotifications(notificationDoc, uid, notificationCallback)

                            }




                        }
                }


                override fun failedToRetrieveRole(e: Exception) {
                    Log.d(TAG, "Retrieving of role with failed because of: $e")
                    firebaseCallback.taskFailed(Exception("Role couldn't be retireved"))
                }

            })


        } else
            firebaseCallback.taskFailed(NullPointerException("The UID of current user is empty"));

     }

    fun getNotifications(
        notificationDoc: DocumentSnapshot,
        uid: String,
        notificationCallback: FireBaseNotification_Callback
    ) {

        val coronaCaseID = notificationDoc.get("coronaCaseID")

        if (coronaCaseID != null) {
            val tmp_notification = CoronaNotification()
            getCoronaCase(coronaCaseID as String,
                object : FireBaseCoronaCase_Callback {

                    override fun finishedLoadingCase(case: CoronaCase) {
                        tmp_notification.coronaCase = case

                        Log.d(
                            TAG,
                            "Successfully added corona case to notification."
                        )

                        tmp_notification.date =
                            notificationDoc.get("date").toString()
                        tmp_notification.time =
                            notificationDoc.get("time").toString()
                        tmp_notification.notificationRadius =
                            notificationDoc.get("notificationRadius")
                                .toString().toInt()
                        tmp_notification.reporter =
                            notificationDoc.get("reporter")
                                .toString()

                        notifications.add(tmp_notification)

                        Log.d(
                            TAG,
                            "------ Added notification of user with UID $uid ------"
                        )

                        if(notifications.size == number_of_notifications){
                            notificationCallback.finishedRetrieveNotification(notifications)
                        }

                    }

                    override fun failedLoadingCase() {
                        tmp_notification.coronaCase =
                            CoronaCase()
                    }

                })


        }

    }


    fun getCoronaCase(coronacaseID: String, callback: FireBaseCoronaCase_Callback) {

        var coronaCase = CoronaCase()

        dB.collection("corona_case").document(coronacaseID).get()
            .addOnSuccessListener {

                coronaCase.case_date = it.get("case_date") as String

                var tmp_status = it.get("corona_status") as String

                when (tmp_status) {
                    Corona_Status.NEGATIVE.toString() -> coronaCase.corona_status =
                        Corona_Status.NEGATIVE
                    Corona_Status.POSITIVE.toString() -> coronaCase.corona_status =
                        Corona_Status.POSITIVE
                    Corona_Status.SUSPECTED.toString() -> coronaCase.corona_status =
                        Corona_Status.SUSPECTED
                    else -> {
                        coronaCase.corona_status = Corona_Status.UNKNOWN
                    }
                }

                val tmp_activities = it.get("activities") as ArrayList<HashMap<String, String>>

                for (i in tmp_activities.indices) {
                    val tmp_activity: Activity =
                        Activity()

                    tmp_activity.titel = tmp_activities[i].get("titel").toString()
                    tmp_activity.place = tmp_activities[i].get("place").toString()
                    tmp_activity.date = tmp_activities[i].get("date").toString()
                    tmp_activity.time = tmp_activities[i].get("time").toString()
                    tmp_activity.description = tmp_activities[i].get("description").toString()

                    activities.add(tmp_activity)

                }

                coronaCase.activities = activities

                callback.finishedLoadingCase(coronaCase)

            }
            .addOnFailureListener() {
                Log.d(TAG, it.localizedMessage)
                callback.failedLoadingCase()
            }


    }


}
