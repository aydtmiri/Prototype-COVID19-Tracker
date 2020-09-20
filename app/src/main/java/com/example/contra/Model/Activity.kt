package com.example.contra.Model

import java.io.Serializable

class Activity(_titel:String="",_date:String="",_time:String="",_place:String="",_description:String="") :Serializable {

     var titel:String
     var date : String
     var time:String
     var place : String
     var description:String

    init{
        titel = _titel
        date = _date
        time =_time
        place = _place
        description = _description
    }


}