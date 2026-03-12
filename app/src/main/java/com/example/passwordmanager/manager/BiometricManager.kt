package com.example.passwordmanager.manager

import android.content.Context
import androidx.biometric.BiometricManager

class BiometricManager(private val context: Context) {
    fun canUseBiometrics(): Boolean{
        val biometricManager = BiometricManager.from(context)

        return biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        ) == BiometricManager.BIOMETRIC_SUCCESS
    }
}