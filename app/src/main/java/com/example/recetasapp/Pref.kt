package com.example.recetasapp

import android.content.Context

class Pref(val context: Context) {

    val SHARED_NAME= "Mydtb"
    val SHARED_USER_NAME= "username"
    val storage= context.getSharedPreferences(SHARED_NAME,0)

    fun saveName(name:String){
        storage.edit().putString(SHARED_USER_NAME, name).apply()
    }
    fun getName():String{
       return storage.getString(SHARED_USER_NAME,"")!!
    }
}