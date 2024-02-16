// Task:  To create a class and override its toString() method to return a stringified JSON object and
//converting it to JSON object and then mapping it to back to class and then printing that object

package org.example

import org.json.JSONObject
import com.google.gson.Gson
import java.lang.reflect.Field
import java.util.Objects

data class Vehicle(var regNo : String?, var chasisNo : String?, var noOfWheels : Int?){
    constructor(): this(regNo = null, chasisNo = null, noOfWheels = null)
    override fun toString(): String {
        val vehicleJson = JSONObject()       //we generally use this
        vehicleJson.put("regNo",regNo)
        vehicleJson.put("chasisNo",chasisNo)
        vehicleJson.put("noOfWheels",noOfWheels)
        return vehicleJson.toString()
//        val vehicleJson = JSONObject(this)
//        return vehicleJson.toString()
    }
}

fun main() {
    val vehicle = Vehicle().apply {
        this.regNo = "423432"
        this.chasisNo = "543285"
        this.noOfWheels = 4
    }
    println(vehicle)     //vehicle json string

    val vehicleJson = JSONObject(vehicle)   //vehicle json object

    //using gson for mapping
    val gson = Gson()
    val secondVehicle: Vehicle = gson.fromJson(vehicleJson.toString(), Vehicle::class.java)
    println(secondVehicle)

}