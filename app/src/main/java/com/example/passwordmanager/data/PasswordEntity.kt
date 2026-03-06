package com.example.passwordmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true)  val id: Int = 0,
    val service: String,
    val login: String,
    val password: String
)
