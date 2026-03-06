package com.example.passwordmanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddPasswordScreen(onSave: (String, String, String) -> Unit){
    var service by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text(text = "Добавить новый пароль", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(value = service, onValueChange = {service = it}, label = {Text("Сервис")}, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = login, onValueChange = {login = it}, label = {Text("Логин")}, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = password, onValueChange = {password = it}, label = {Text("Пароль")}, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = { if(service.isNotBlank()) onSave(service, login, password)},
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ){
            Text("Сохранить")
        }
    }
}