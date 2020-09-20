package com.example.contra.View

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.contra.R
import kotlinx.android.synthetic.main.activity_dialog.*
import java.lang.ClassCastException

class ActivityDialog : AppCompatDialogFragment() {

     var editTitle: EditText?
     var editDate: EditText?
     var editTime: EditText?
     var editLocation : EditText?
     var editDescription: EditText?

    lateinit var listener: ActivityDialogListener

    init{
        editTitle = ui_a_editTitle
        editDate = ui_a_editDate
        editTime = ui_a_editTime
        editDescription = ui_a_editDescription
        editLocation = ui_a_editLocation
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        val inflater: LayoutInflater = requireActivity().layoutInflater
        val view : View = inflater.inflate(R.layout.activity_dialog,null)

        builder.setView(view)
            .setTitle(R.string.activityDialogTitle)
            .setNegativeButton(R.string.button_cancel, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }


            })
            .setPositiveButton(R.string.button_add, object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                    editTitle = view.findViewById(R.id.ui_a_editTitle)
                    editDate = view.findViewById(R.id.ui_a_editDate)
                    editTime = view.findViewById(R.id.ui_a_editTime)
                    editDescription = view.findViewById(R.id.ui_a_editDescription)
                    editLocation = view.findViewById(R.id.ui_a_editLocation)

                    val title = editTitle?.text.toString()
                    val date = editDate?.text.toString()
                    val time = editTime?.text.toString()
                    val location = editLocation?.text.toString()
                    val description = editDescription?.text.toString()

                    listener.applyEntries(title,date,time,location,description)

                }

            })





        return builder.create()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as ActivityDialogListener
        } catch (e: ClassCastException) {

            throw ClassCastException(context.toString() + "must implement ActivityDialogListener.")
        }
    }

    interface ActivityDialogListener{
        fun applyEntries(editTitle:String, editDate: String, editTime:String,editLocation:String, editDescription:String)
    }
}