package com.example.contra.View

import android.os.Build
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contra.Model.CoronaNotification
import com.example.contra.R
import kotlinx.android.synthetic.main.activity_private_user_notification_details.*

class PrivateUserNotificationDetails : AppCompatActivity() {

    lateinit var notification : CoronaNotification
    lateinit var adapter : AdapterRecyclingView
    lateinit var recyclingView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_user_notification_details)

        val aktuellerIntent = this.intent
        notification = aktuellerIntent.getSerializableExtra ("NOTIFICATION") as CoronaNotification

        initDialog()

    }

    fun initDialog(){
        if(!notification.date.equals("") && notification.date != null)
            show_pund_date.text = notification.date
        else
            ui_pund_date.setText(R.string.pund_no_date_available)
        if(notification.notificationRadius != null)
            pund_show_radius.text = notification.notificationRadius.toString() +" km"

        if(notification.coronaCase.activities.size > 0){
            recyclingView = pund_recyclerView
            recyclingView.layoutManager = LinearLayoutManager(this)
            adapter = AdapterRecyclingView(
                this,
                notification.coronaCase.activities
            )
            recyclingView.adapter = adapter

            adapter.setOnItemClickListener(object : AdapterRecyclingView.OnItemClickListener {
                @RequiresApi(Build.VERSION_CODES.KITKAT)
                override fun onItemClick(position: Int) {

                    var details =
                        pund_recyclerView[position].findViewById<ConstraintLayout>(R.id.activityDetailsView)
                    var showHideText =
                        pund_recyclerView[position].findViewById<TextView>(R.id.activityShowHideDetail)


                    val autoTransition = AutoTransition()
                    autoTransition.setDuration(100)

                    TransitionManager.beginDelayedTransition(
                        pund_recyclerView[position] as ViewGroup?,
                        autoTransition
                    )

                    if (details.visibility == View.VISIBLE) {

                        details.visibility = View.GONE
                        showHideText.setText(R.string.moreDetails)
                    } else
                        if (details.visibility == View.GONE) {
                            details.visibility = View.VISIBLE
                            showHideText.setText(R.string.lessDetails)
                        }


                }

            })

        }


    }
}
