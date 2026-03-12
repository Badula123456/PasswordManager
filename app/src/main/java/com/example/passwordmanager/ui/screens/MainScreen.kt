package com.example.passwordmanager.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.passwordmanager.data.PasswordEntity
import com.example.passwordmanager.ui.element.PasswordCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(passwords: List<PasswordEntity>,
               onAddClick: () -> Unit,
               onDelete: (PasswordEntity) -> Unit,
               onSettingsClick: () -> Unit) {
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Мои пароли")},
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, contentDescription = "Настройки")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }

    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(passwords) { entry ->
                PasswordCard(
                    entry,
                    onDelete = { onDelete(entry)}
                )
            }
        }
    }
}