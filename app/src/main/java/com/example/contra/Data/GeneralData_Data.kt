package com.example.contra.Data

import android.util.Log
import com.example.contra.Model.FireBaseRole_Callback
import com.example.contra.Model.Role
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class GeneralData_Data {

    val dB = Firebase.firestore

    fun getCurrentUserId(): String {

        if ((FirebaseAuth.getInstance().currentUser?.uid != null) or (!FirebaseAuth.getInstance().currentUser?.uid.equals(
                ""
            ))
        )
            return FirebaseAuth.getInstance().currentUser?.uid.toString()
        else
            throw NullPointerException("Es wurde keine UID gefunden.")

    }

    fun getUserToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener {
                Log.d("GET_TOKEN", "Token was successfully received.")
                return@addOnSuccessListener

            }

            .addOnSuccessListener{
                throw NullPointerException("Es wurde kein Token gefunden.")
            }

    }

    fun updateField(collectionPath:String, documentPath: String, fieldName: String,fieldValue:String){
        dB.collection(collectionPath).document(documentPath).update(fieldName,fieldValue)
            .addOnSuccessListener {
                Log.d("UpdateField","Successfully updated field $fieldName of document $documentPath in collection $collectionPath")
            }
            .addOnFailureListener{
                Log.d("UpdateField_ERROR","Could not updated field $fieldName of document $documentPath in collection $collectionPath")
            }
    }
    fun updateFieldInCollection(collectionPath:String, documentPath: String, fieldName: String,fieldValue:String){
        dB.collection(collectionPath).document(documentPath).update(fieldName,FieldValue.arrayUnion(fieldValue))
            .addOnSuccessListener {
                Log.d("UpdateField","Successfully updated field $fieldName of document $documentPath in collection $collectionPath")
            }
            .addOnFailureListener{
                Log.d("UpdateField_ERROR","Could not updated field $fieldName of document $documentPath in collection $collectionPath")
            }
    }

    fun getUserRole(callback: FireBaseRole_Callback) {

        var uid = getCurrentUserId()

        dB.collection("roles").document(uid).get()
            .addOnSuccessListener {

                Log.d("GET_ROLE", "-------start of getRole-------")
                val tmp_role = it.get("role")

                if (tmp_role != null) {

                    if (tmp_role.equals(Role.PRIVATE_USER.toString())){
                        Log.d("GET_ROLE", "Found role : ${Role.PRIVATE_USER}")
                        callback.retrievedRole(Role.PRIVATE_USER)
                    }

                    else
                        if (tmp_role.equals(Role.SPECIALIST.toString())){
                            Log.d("GET_ROLE", "Found role : ${Role.SPECIALIST}")
                            callback.retrievedRole(Role.SPECIALIST)
                        }

                        else
                            callback.failedToRetrieveRole(Exception("Role of user with uid: $uid is unknown"))
                } else
                    callback.failedToRetrieveRole(Exception("Role of user with uid: $uid is null"))


            }
            .addOnFailureListener {
                callback.failedToRetrieveRole(Exception("Role document of given user couldn't be retrieved"))
            }

    }


}
