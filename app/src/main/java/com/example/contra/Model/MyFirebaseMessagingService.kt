package com.example.contra.Model

import android.content.ContentValues.TAG
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        /*val ref = FirebaseFirestore.getInstance().collection("tokens").document(token)
        ref.set(token)
            .addOnSuccessListener {
            Log.d("TOKEN","Successfully added token to database.")
        }

            .addOnFailureListener{
                Log.d("TOKEN","Couldn't add token to database.")
            }

    }*/

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        handleMessage(remoteMessage)
    }

    private fun handleMessage(remoteMessage: RemoteMessage) {
        //1
        val handler = Handler(Looper.getMainLooper())

        //2
        handler.post(Runnable {

        }
        )
    }


}
