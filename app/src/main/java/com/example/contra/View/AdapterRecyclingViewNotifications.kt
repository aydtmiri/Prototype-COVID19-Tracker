package com.example.contra.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contra.Model.CoronaNotification
import com.example.contra.R
import kotlinx.android.synthetic.main.custom_notification_recycler_view.view.*

class AdapterRecyclingViewNotifications(_context:Context, _data: MutableList<CoronaNotification>) : RecyclerView.Adapter<AdapterRecyclingViewNotifications.ViewHolder>() {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(_context)
    private var data: MutableList<CoronaNotification> = _data
    lateinit var mListener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener){
        mListener =listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view = layoutInflater.inflate((R.layout.custom_notification_recycler_view), parent,false)
        return ViewHolder(
            view,
            mListener
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.notificationTitle.text = data[position].coronaCase.corona_status.toString()
        holder.notificationDateTime.text = data[position].date
        holder.notificationRadius.text = data[position].notificationRadius.toString() + " km"

    }


    class ViewHolder(itemView: View,listener : OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        var notificationTitle: TextView = itemView.notificationTitle
        var notificationDateTime: TextView = itemView.notificationDateTime
        var notificationRadius: TextView = itemView.notificationRadius
        var notifiactionDetails : TextView = itemView.notificationShowHideDetail

        init {
            itemView.notificationShowHideDetail.setOnClickListener(object: View.OnClickListener{
                override fun onClick(v: View?) {

                    if(listener != null){
                        var position = adapterPosition

                        if(position != RecyclerView.NO_POSITION)
                            listener.onItemClick(position)
                    }
                }

            })
        }


    }




}