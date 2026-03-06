package com.example.passwordmanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.passwordmanager.manager.PinManager

@Composable
fun PinScreen(
    pinManager: PinManager,
    onSuccess: () -> Unit
) {
    var inputPin by remember { mutableStateOf("") }
    val isSetupMode = !pinManager.isPinSet() // Если ПИН не создан, включаем режим настройки

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isSetupMode) "Придумайте ПИН-код" else "Введите ПИН-код",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Поле ввода (в реальном приложении лучше сделать кнопками 0-9)
        OutlinedTextField(
            value = inputPin,
            onValueChange = { if (it.length <= 4) inputPin = it },
            label = { Text("ПИН (4 цифры)") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Button(
            onClick = {
                if (isSetupMode) {
                    if (inputPin.length == 4) {
                        pinManager.savePin(inputPin)
                        onSuccess()
                    }
                } else {
                    if (pinManager.checkPin(inputPin)) {
                        onSuccess()
                    } else {
                        // Тут можно добавить показ ошибки "Неверный ПИН"
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Подтвердить")
        }
    }
}