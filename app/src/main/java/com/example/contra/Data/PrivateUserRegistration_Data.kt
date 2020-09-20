package com.example.contra.Data

import android.util.Log
import com.example.contra.Model.FireBase_Callback
import com.example.contra.Model.PrivateUserC
import com.example.contra.Model.Role
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import org.imperiumlabs.geofirestore.GeoFirestore
import java.lang.Exception

class PrivateUserRegistration_Data {

    val TOKEN ="Registration"

    fun register(email: String, password: String, private_user: PrivateUserC, firebaseCallback: FireBase_Callback) {


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnFailureListener() {
                Log.d(
                    TOKEN, "onComplete: Failed=" + (it
                        ?.getLocalizedMessage())
                );


                firebaseCallback.taskFailed(Exception("joa"))
            }
            .addOnSuccessListener {

                Log.d(
                    TOKEN,
                    "Successfully created firebase user with uid: ${FirebaseAuth.getInstance().currentUser?.uid}"
                )

                //---------------------------------------Add additional user data to database ---------------------------------------//

                addAddtionalUserInformation(private_user,FirebaseAuth.getInstance().currentUser?.uid,firebaseCallback)

            }
    }

    private fun addAddtionalUserInformation(private_user: PrivateUserC, user_id: String?, firebaseCallback: FireBase_Callback) {


        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener {

                val dB = Firebase.firestore
                val userRef = user_id?.let { dB.collection("private_user").document(it) }
                val roleRef = user_id?.let { dB.collection("roles").document(it) }
                val tokenRef = dB.collection("tokens").document(it.id)

                //---------------------------------------start batch writer ---------------------------------------//
                dB.runBatch { batch ->

                    Log.d(TOKEN, "-------------------START OF BATCH-------------------")

                    if (tokenRef != null) {
                        val token_entry = hashMapOf(
                            "token" to it.token
                        )

                        batch.set(tokenRef,token_entry)

                        Log.d(TOKEN, "--------Set token-------")

                        if (userRef != null) {
                            batch.set(userRef, private_user)
                            batch.update(userRef, "token", it.token)

                            Log.d(TOKEN, "--------Set user-------")
                        }
                    }


                    if (roleRef != null) {

                        val role = hashMapOf(
                            "role" to Role.PRIVATE_USER.toString()
                        )
                        batch.set(roleRef, role)

                        Log.d(TOKEN, "--------Set role-------")
                    }

                    Log.d(TOKEN, "-------------------END OF BATCH-------------------")


                    //---------------------------------------end batch writer ---------------------------------------//
                }.addOnSuccessListener {
                    Log.d(TOKEN, "Successfully saved user data")

                    Log.d(TOKEN, "-------------------START OF LOCATION FINDER-------------------")
                    //---------------------------------------start location finder ---------------------------------------//
                    val collection_ref = FirebaseFirestore.getInstance().collection("user_location")
                    val geoFirestore = GeoFirestore(collection_ref)

                    geoFirestore.setLocation(
                        user_id,
                        GeoPoint(private_user.location.latitude, private_user.location.longitude)
                    )
                    Log.d(TOKEN, "Location saved on server successfully!")
                    Log.d(TOKEN, "-------------------END OF LOCATION FINDER-------------------")
                    firebaseCallback.finishedTask()

                }
                    .addOnFailureListener {
                        Log.d(
                            TOKEN,
                            "Following Error occuried during saving process :" + it.localizedMessage
                        )
                        FirebaseAuth.getInstance().currentUser?.delete()
                        firebaseCallback.taskFailed(Exception("it.localizedMessage"))
                    }

            }
            .addOnFailureListener{
                Log.d(
                    TOKEN,
                    "Couldn't get token of device because of :" + it.localizedMessage
                )
                firebaseCallback.taskFailed(Exception("Couldn't get token of device"))
            }

    }

}






