package com.example.passwordmanager.manager

import java.security.MessageDigest

object SecurityUtils {
    fun hashPin(pin: String): String{
        val bytes = pin.toByteArray() //Строка превращается в массив байтов
        val md  = MessageDigest.getInstance("SHA-256") //Создаётся объект MessageDigest с алгоритмом SHA-256
        val digest = md.digest(bytes) //Происходит вычисление хэша
        return digest.fold(""){str, it -> str + "%02x".format(it)} //Каждый байт переводится в hex формат
    }
}