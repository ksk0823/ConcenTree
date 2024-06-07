package com.example.concentree.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TreeDao {

    @Query("SELECT * FROM tree")
    fun getAllTrees(): Flow<List<Tree>>

    // 구매 된 (키우기 가능한) 나무들 목록 가저오기
    @Query("SELECT * FROM tree where isPurchased = true")
    fun getTreesToGrow(): Flow<List<Tree>>

    // 상점에서 구매 가능한 나무들 가져오기
    @Query("SELECT * FROM tree where isPurchased = false")
    fun getTreesInShop(): Flow<List<Tree>>

    @Query("SELECT * FROM tree WHERE id = :id")
    suspend fun getTreeById(id: Int): Tree?

    @Insert
    suspend fun InsertTree(tree: Tree)

    @Update
    suspend fun UpdateTree(tree: Tree)

    @Delete
    suspend fun DeleteTree(tree: Tree)

}
