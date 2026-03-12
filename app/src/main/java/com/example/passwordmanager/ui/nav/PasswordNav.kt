package com.example.passwordmanager.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.passwordmanager.manager.PinManager
import com.example.passwordmanager.ui.screens.AddPasswordScreen
import com.example.passwordmanager.ui.screens.MainScreen
import com.example.passwordmanager.ui.screens.PinScreen
import com.example.passwordmanager.ui.screens.SettingsScreen
import com.example.passwordmanager.viewmodel.PasswordViewModel

@Composable
fun PasswordNav(viewModel: PasswordViewModel) {
    val navController = rememberNavController()
    val passwords by viewModel.allPasswords.collectAsState()

    val context = LocalContext.current
    val pinManager = PinManager(context)

    NavHost(navController = navController, startDestination = "pin") {
        composable("pin") {
            PinScreen(
                pinManager = pinManager,
                onSuccess = {
                    navController.navigate("list") {
                        popUpTo("pin") { inclusive = true }
                    }
                }
            )
        }

        composable("list") {
            MainScreen(
                passwords = passwords,
                onAddClick = {
                    navController.navigate("add")},
                onDelete = { viewModel.deletePassword(it)},
                onSettingsClick = { navController.navigate("settings")}
            )
        }
        composable("add") {
            AddPasswordScreen { service, login, password ->
                viewModel.addPassword(service = service, login = login, password = password)
                navController.popBackStack()
            }
        }
        composable("settings") {
            SettingsScreen(pinManager)
        }
    }
}