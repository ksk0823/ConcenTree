package com.example.concentree.viewmodel

import com.example.concentree.roomDB.AppDatabase
import com.example.concentree.roomDB.User


class UserRepository(private val db: AppDatabase) {

    val userDao = db.getUserDao()

    suspend fun InsertUser(user: User) {
        userDao.InsertUser(user)
    }

    suspend fun UpdateUser(user: User){
        userDao.UpdateUser(user)
    }
    suspend fun DeleteUser(user: User){
        userDao.DeleteUser(user)
    }

    suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)
    }

    suspend fun updateUserCoins(id: Int, coins: Int) {
        userDao.updateUserCoins(id, coins)
    }
}

