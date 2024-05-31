package com.example.concentree.viewmodel

import com.example.concentree.roomDB.AppDatabase
import com.example.concentree.roomDB.User


class UserRepository(private val db: AppDatabase) {

    val userDao = db.getUserDao()

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)
    }

    suspend fun updateUserCoins(id: Int, coins: Int) {
        userDao.updateUserCoins(id, coins)
    }
}

