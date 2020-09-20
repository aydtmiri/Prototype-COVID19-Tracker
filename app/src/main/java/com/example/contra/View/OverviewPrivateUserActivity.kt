package com.example.contra.View

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contra.Controller.OverviewPrivateUser_Controller
import com.example.contra.Data.GetCoronaNotification_Data
import com.example.contra.Model.CoronaNotification
import com.example.contra.Model.FireBaseNotification_Callback
import com.example.contra.Model.FireBase_Callback
import com.example.contra.R
import kotlinx.android.synthetic.main.activity_overview_private_user.*
import java.io.Serializable

class OverviewPrivateUserActivity : AppCompatActivity() {

    val TAG = "OVERVIEW_USER"
    lateinit var adapter: AdapterRecyclingViewNotifications
    lateinit var recyclingView: RecyclerView
    var notifications = mutableListOf<CoronaNotification>()
    var intentDetails = Intent()
    var controller = OverviewPrivateUser_Controller()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview_private_user)

        intentDetails = Intent(this, PrivateUserNotificationDetails::class.java)
        getNotifications()


    }


    private fun getNotifications() {

        controller.getCoronaNotifications(object :
            FireBase_Callback {
            override fun finishedTask() {

            }

            override fun taskFailed(e: Exception) {
                Log.d(TAG, e.localizedMessage)
            }

        }, object : FireBaseNotification_Callback {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun finishedRetrieveNotification(_notifications: MutableList<CoronaNotification>) {
                notifications = _notifications
                notificationRecyclerView()
            }

            override fun taskFailed(e: Exception) {
                Log.d(TAG, e.localizedMessage)
            }

        })

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun notificationRecyclerView() {

        recyclingView = puo_recyclerView
        recyclingView.layoutManager = LinearLayoutManager(this)
        adapter = AdapterRecyclingViewNotifications(
            this,
            notifications
        )
        recyclingView.adapter = adapter

        val autoTransition = AutoTransition()
        autoTransition.setDuration(100)

        TransitionManager.beginDelayedTransition(
            puo_recyclerView as ViewGroup?,
            autoTransition
        )

        adapter.setOnItemClickListener(object :
            AdapterRecyclingViewNotifications.OnItemClickListener {
            override fun onItemClick(position: Int) {
                intentDetails.putExtra("NOTIFICATION", notifications[position] as Serializable)
                startActivity(intentDetails)


            }

        })


    }
}


