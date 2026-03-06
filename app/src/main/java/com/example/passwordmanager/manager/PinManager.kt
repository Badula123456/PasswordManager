package com.example.passwordmanager.manager

import android.content.Context

class PinManager(context: Context) {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun isPinSet(): Boolean = prefs.contains("pin_hash")

    fun savePin(pin: String){
        val hash = SecurityUtils.hashPin(pin)
        prefs.edit().putString("pin_hash", hash).apply()
    }

    fun checkPin(pin: String): Boolean{
        val savedHash = prefs.getString("pin_hash", null)
        return savedHash == SecurityUtils.hashPin(pin)
    }
}