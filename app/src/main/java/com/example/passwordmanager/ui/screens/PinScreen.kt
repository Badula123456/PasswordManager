package com.example.passwordmanager.ui.screens

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.passwordmanager.manager.PinManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.passwordmanager.ui.element.PinButton

@Composable
fun PinScreen(
    pinManager: PinManager,
    onSuccess: () -> Unit
) {
    var inputPin by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val isSetupMode = !pinManager.isPinSet()

    val context = LocalContext.current
    val activity = context as FragmentActivity

    val executor = ContextCompat.getMainExecutor(context)

    val biometricEnabled = pinManager.isBiometricEnabled()


    val biometricPrompt = BiometricPrompt(
        activity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationSucceeded(
                result: BiometricPrompt.AuthenticationResult
            ) {
                super.onAuthenticationSucceeded(result)

                onSuccess()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }
        }
    )

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Вход в менеджер паролей")
        .setSubtitle("Подтвердите отпечаток пальца")
        .setNegativeButtonText("Использовать PIN")
        .build()

    val biometricManager = BiometricManager.from(context)

    val canUseBiometric =
        biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        ) == BiometricManager.BIOMETRIC_SUCCESS


    LaunchedEffect(Unit) {
        if (!isSetupMode && canUseBiometric && biometricEnabled) {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly // Распределяем элементы по вертикали
    ) {
        // 1. Заголовок
        Text(
            text = if (isSetupMode) "Придумайте ПИН-код" else "Введите ПИН-код",
            style = MaterialTheme.typography.headlineMedium
        )

        // 2. Индикаторы ввода (точки)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(vertical = 32.dp)
        ) {
            repeat(4) { index ->
                val isFilled = index < inputPin.length
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(
                            if (isError) MaterialTheme.colorScheme.error
                            else if (isFilled) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outlineVariant
                        )
                )
            }
        }

        if (isError) {
            Text("Неверный ПИН-код", color = MaterialTheme.colorScheme.error)
        }

        // 3. Цифровая клавиатура
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val buttons = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf("OK", "0", "C") // Заменили "" на "OK"
            )

            buttons.forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    row.forEach { digit ->
                        PinButton(digit) {
                            when (digit) {
                                "C" -> {
                                    if (inputPin.isNotEmpty()) inputPin = inputPin.dropLast(1)
                                    isError = false
                                }
                                "OK" -> {
                                    // Проверяем ПИН только при нажатии OK
                                    if (inputPin.length >= 4) { // или строго == 4
                                        handlePinInput(inputPin, isSetupMode, pinManager, onSuccess) {
                                            isError = true
                                            inputPin = ""
                                        }
                                    }
                                }
                                else -> {
                                    if (inputPin.length < 4) {
                                        inputPin += digit
                                        isError = false
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Вспомогательная функция для логики
private fun handlePinInput(
    pin: String,
    isSetup: Boolean,
    manager: PinManager,
    onSuccess: () -> Unit,
    onError: () -> Unit
) {
    if (isSetup) {
        manager.savePin(pin)
        onSuccess()
    } else {
        if (manager.checkPin(pin)) onSuccess() else onError()
    }
}