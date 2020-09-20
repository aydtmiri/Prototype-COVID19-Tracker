package com.example.contra.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.contra.Model.Activity
import com.example.contra.R
import kotlinx.android.synthetic.main.custom_activity_recycler_view.view.*

class AdapterRecyclingView(_context:Context, _data: MutableList<Activity>) : RecyclerView.Adapter<AdapterRecyclingView.ViewHolder>() {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(_context)
    private var data: MutableList<Activity> = _data
    lateinit var mListener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : OnItemClickListener){
        mListener =listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view = layoutInflater.inflate((R.layout.custom_activity_recycler_view), parent,false)
        return ViewHolder(
            view,
            mListener
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.activityTitle.text = data[position].titel
        holder.activityDate.text = data[position].date
        holder.activityTime.text = data[position].time
        holder.activityLocation.text = data[position].place
        holder.activityDescription.text = data[position].description


    }


    class ViewHolder(itemView: View,listener : OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        var activityTitle: TextView = itemView.activityTitle
        var activityDate: TextView = itemView.activityDate
        var activityTime: TextView = itemView.activityTime
        var activityLocation: TextView = itemView.activityLocation
        var activityDescription: TextView = itemView.activityDescription
        var activityDetailView : ConstraintLayout = itemView.activityDetailsView
        var activityHideShow : TextView = itemView.activityShowHideDetail

        init {
            itemView.activityShowHideDetail.setOnClickListener(object: View.OnClickListener{
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