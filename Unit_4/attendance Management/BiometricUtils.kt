package com.example.biometricsattendance

import android.content.Context
import android.content.SharedPreferences
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class BiometricUtils(private val context: Context) {

    private val biometricSharedPreferences: SharedPreferences =
        context.getSharedPreferences("BiometricPrefs", Context.MODE_PRIVATE)

    fun isBiometricAvailable(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun registerBiometric(
        executor: Executor,
        callback: BiometricPrompt.AuthenticationCallback
    ) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Register your biometric")
            .setNegativeButtonText("Cancel")
            .build()

        val biometricPrompt = BiometricPrompt(context as HomeActivity, executor, callback)
        biometricPrompt.authenticate(promptInfo)
    }

    fun saveBiometricStatus(isRegistered: Boolean) {
        val editor = biometricSharedPreferences.edit()
        editor.putBoolean("biometric_registered", isRegistered)
        editor.apply()
    }

    fun isBiometricRegistered(): Boolean {
        return biometricSharedPreferences.getBoolean("biometric_registered", false)
    }
}
