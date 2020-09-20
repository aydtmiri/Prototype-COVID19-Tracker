package com.example.contra.Data

import android.util.Log
import com.example.contra.Model.CoronaNotification
import com.example.contra.Model.FireBase_Callback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import org.imperiumlabs.geofirestore.GeoFirestore
import java.lang.Exception


class SpecialistAddCoronaCase_Data {

    val TAG = "ADD_CASE"
    val generalData = GeneralData_Data()
    private lateinit var functions: FirebaseFunctions


    fun registerCoronaNotification(_coronaNotification: CoronaNotification, firebaseCallback: FireBase_Callback) {

        var userIDs = mutableSetOf<String>()
        var userTokens = mutableSetOf<String>()
        functions = Firebase.functions

        //---------------------------------------init variables ---------------------------------------//


        //----------init dB variables ---------------//
        val dB = Firebase.firestore
        val caseRef = dB.collection("corona_case").document()
        val notificationRef = dB.collection("corona_notification").document()
        val specialistRef = FirebaseAuth.getInstance().uid?.let {
            dB.collection("specialist").document(it)
        }

        var coronaCaseID = ""
        var notificationID = ""

        //---------------------------------------start batch writer ---------------------------------------//
        dB.runBatch { batch ->

            //--------------------------------------- save corona case ---------------------------------------//
            batch.set(caseRef, _coronaNotification.coronaCase)
            coronaCaseID = caseRef.id

            //--------------------------------------- save corona notification ---------------------------------------//

            val coronaNotification = hashMapOf(
                "coronaCaseID" to coronaCaseID,
                "time" to _coronaNotification.time,
                "date" to _coronaNotification.date,
                "notificationRadius" to _coronaNotification.notificationRadius,
                "reporter" to _coronaNotification.reporter,
                "affectedUser" to _coronaNotification.affectedUser
            )

            batch.set(notificationRef, coronaNotification)
            notificationID = notificationRef.id

            //--------------------------------- save corona notification to specialist ---------------------------//

            if (specialistRef != null) {
                batch.update(specialistRef, "coronaNotification", FieldValue.arrayUnion(notificationID))
            }

            //---------------------------------------end batch writer ---------------------------------------//
        }.addOnSuccessListener {
            Log.d(TAG, "Successfully saved notification")


            //---------------------------------------start location finder ---------------------------------------//

            val collection_ref = FirebaseFirestore.getInstance().collection("corona_location")
            val geoFirestore = GeoFirestore(collection_ref)

            geoFirestore.setLocation(
                notificationID,
                GeoPoint(
                    _coronaNotification.coronaCase.patient_location.latitude,
                    _coronaNotification.coronaCase.patient_location.longitude
                )
            )
            Log.d(TAG, "Successfully saved corona case location.")

            firebaseCallback.finishedTask()



        }
            .addOnFailureListener{
                Log.d(TAG, "Couldn't save notification. ERROR: ${it.localizedMessage}")
                firebaseCallback.taskFailed(Exception("Couldn't save notification.ERROR: ${it.localizedMessage}"))
            }
    }

}
