package com.example.concentree.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    suspend fun InsertUser(user: User)

    @Update
    suspend fun UpdateUser(user: User)

    @Delete
    suspend fun DeleteUser(user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    // 상점에서 나무 구매 후 코인 수 업데이트
    @Query("UPDATE user SET coins = :coins WHERE id = :id")
    suspend fun updateUserCoins(id: Int, coins: Int)

}
