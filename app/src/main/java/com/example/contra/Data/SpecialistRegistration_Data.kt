package com.example.contra.Data

import android.util.Log
import com.example.contra.Model.FireBase_Callback
import com.example.contra.Model.Role
import com.example.contra.Model.Specialist_C
import com.firebase.geofire.GeoFire
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class SpecialistRegistration_Data {

    private var geoFire: GeoFire? = null
    private lateinit var auth: FirebaseAuth


    fun register(email: String, password: String, specialist: Specialist_C, firebaseCallback: FireBase_Callback) {


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnFailureListener() {
                Log.d(
                    "REGISTRATION_FAILED", "onComplete: Failed=" + (it
                        ?.getLocalizedMessage())
                );

                firebaseCallback.taskFailed(Exception(it.localizedMessage))
            }
            .addOnSuccessListener {

                Log.d(
                    "UserRegistration",
                    "Successfully created user with uid: ${FirebaseAuth.getInstance().currentUser?.uid}"
                )

                //---------------------------------------Add additional user data to database ---------------------------------------//
                    addNewUserData(specialist, FirebaseAuth.getInstance().currentUser?.uid, firebaseCallback)

            }
    }


    private fun addNewUserData(specialist: Specialist_C, user_id: String?, firebaseCallback : FireBase_Callback) {

         val token = FirebaseInstanceId.getInstance().instanceId
             .addOnSuccessListener {

                 //---------------------------------------get database ---------------------------------------//
                 val dB = Firebase.firestore

                 val userRef = user_id?.let { dB.collection("specialist").document(it) }
                 val roleRef = user_id?.let { dB.collection("roles").document(it) }
                 //---------------------------------------start batch writer ---------------------------------------//
                 dB.runBatch { batch ->


                         if (userRef != null) {

                             batch.set(userRef, specialist)

                         }




                     if (user_id != null) {
                         if (roleRef != null) {

                             val role = hashMapOf(
                                 "role" to Role.SPECIALIST.toString()
                             )

                             batch.set(roleRef,role)
                         }
                     }


                 }
                     .addOnSuccessListener {
                         Log.d("Add_Specialist_Data", "Successfully saved user data")
                         firebaseCallback.finishedTask()
                     }

                     .addOnFailureListener {

                         Log.d("Add_Specialist_Data", "onComplete: Failed=" + (it.getLocalizedMessage()));
                         FirebaseAuth.getInstance().currentUser?.delete()
                         firebaseCallback.taskFailed(it)
                     }

             }
    }
}