package com.example.passwordmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.data.PasswordEntity
import com.example.passwordmanager.data.PasswordsDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PasswordViewModel(private val dao: PasswordsDao) : ViewModel() {
    val allPasswords = dao.getAllPasswords().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addPassword(service: String, login: String, password: String){
        viewModelScope.launch {
            dao.insertPassword(PasswordEntity(service = service, login = login, password = password))
        }
    }

    fun deletePassword(password: PasswordEntity){
        viewModelScope.launch {
            dao.deletePassword(password)
        }
    }

}