package org.example

import org.json.JSONObject

class Vehicle(val regNo : String, val chasisNo : String, val noOfWheels : Int){
    override fun toString(): String {
        return "Vehicle(regNo : $regNo, chasisNo : $chasisNo, noOfWheels : $noOfWheels)"
    }
}

fun main() {
    val v = Vehicle("A123","B123",4)
    val jsonString = JSONObject(v).toString()
    println(jsonString)     //json string
    val jsonObject = JSONObject(jsonString)
    println(jsonObject)     //json object
    val v1 = Vehicle(jsonObject.getString("regNo"),jsonObject.getString("chasisNo"),jsonObject.getInt("noOfWheels"))
    println(v1)
}