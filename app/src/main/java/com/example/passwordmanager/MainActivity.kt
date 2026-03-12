package com.example.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.passwordmanager.data.AppDatabase
import com.example.passwordmanager.ui.nav.PasswordNav
import com.example.passwordmanager.viewmodel.PasswordViewModel


class MainActivity : FragmentActivity() {
    val db by lazy {Room.databaseBuilder(applicationContext, AppDatabase::class.java, "pass-db").build()}

    private val viewModel by viewModels<PasswordViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PasswordViewModel(db.passwordDao()) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PasswordNav(viewModel)
        }
    }
}