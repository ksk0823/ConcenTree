package com.example.concentree.viewmodel

import com.example.concentree.roomDB.User
import com.example.concentree.roomDB.UserDao


class UserRepository (private val userDao: UserDao) {

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

