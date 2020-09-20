package com.example.contra.Model

class Address(_streetAndNr:String ="", _postalCode:Int=0,_city:String="", _country:String="") {

    var streetAndNr: String
    var postalCode: Int
    var city : String
    var country: String

    init{
        streetAndNr =_streetAndNr
        postalCode = _postalCode
        city = _city
        country = _country
    }

    override fun toString():String
    {
        return streetAndNr+","+postalCode+","+city+","+country
    }
}