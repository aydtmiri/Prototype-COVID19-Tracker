package com.example.contra.Controller

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.example.contra.Model.Location
import android.util.Log
import java.util.*

object GetLocationFromAdress_Helper {

     fun getLocationFromAddress(context: Context, address: com.example.contra.Model.Address): Location {

        //--------------------------------------- init values ---------------------------------------//

        val geocoder: Geocoder = Geocoder(context, Locale.getDefault())
        var location_result: List<Address>
        var location: Location =
            Location()


        //--------------------------------------- search for location ---------------------------------------//
         try {
             location_result = geocoder.getFromLocationName(address.toString(), 1)

             if (location_result.isNotEmpty()) {

                 if (location_result[0].thoroughfare.equals(""))
                     throw Exception("Die angegebene Straße wurde nicht gefunden. Bitte überprüfen SIe diese oder geben sie eine Straße in Ihrere Nähe ein.")

                 location.latitude = location_result[0].latitude
                 location.longitude = location_result[0].longitude

                 Log.d(
                     "GetLocattion",
                     "Successfully found following address: " + location_result[0]
                 )
             } else
                 throw Exception("No Address found.")
         }catch(e:Exception){
             Log.d("GetLocattion_ERROR",e.localizedMessage)

             location.latitude = 0.0
             location.longitude = 0.0

         }

        return location


    }


}



