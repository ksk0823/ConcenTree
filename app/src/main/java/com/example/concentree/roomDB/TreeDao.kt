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

    @Insert
    suspend fun insertTree(tree: Tree)

    @Update
    suspend fun UpdateTree(tree: Tree)

    @Delete
    suspend fun DeleteTree(tree: Tree)
}
