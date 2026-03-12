package com.example.passwordmanager.manager

import android.content.Context

class PinManager(context: Context) {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE) //создаётся (или открывается) файл настроек settings, MODE_PRIVATE — файл доступен только этому приложению

    fun isPinSet(): Boolean = prefs.contains("pin_hash")

    fun savePin(pin: String){
        val hash = SecurityUtils.hashPin(pin)
        prefs.edit().putString("pin_hash", hash).apply()
    }

    fun checkPin(pin: String): Boolean{
        val savedHash = prefs.getString("pin_hash", null)
        return savedHash == SecurityUtils.hashPin(pin)
    }

    fun setBiometricEnabled(enabled: Boolean){
        prefs.edit().putBoolean("biometric_enabled", enabled).apply()
    }

    fun isBiometricEnabled(): Boolean{
        return prefs.getBoolean("biometric_enabled", false)
    }

}