package com.example.contra.Model

import com.example.contra.Model.CoronaCase
import java.io.Serializable

class CoronaNotification(_coronaCase: CoronaCase = CoronaCase(), _date: String = "", _time: String ="", _notificationRadius : Int = 0, _reporter: String="") : Serializable{

     var coronaCase: CoronaCase
     var date: String
     var time: String
     var notificationRadius : Int
     var reporter: String
     var affectedUser : MutableList<String> = mutableListOf<String>()

    init {
        coronaCase = _coronaCase
        date = _date
        time = _time
        notificationRadius = _notificationRadius
        reporter = _reporter
    }
}