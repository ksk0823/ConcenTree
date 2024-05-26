package com.example.concentree.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ForestTreeDao {

    @Query("SELECT * FROM forest_tree WHERE startTime >= :startTime AND endTime <= :endTime")
    suspend fun getTreesInRange(startTime: Long, endTime: Long): List<ForestTree>

    @Query("SELECT * FROM forest_tree WHERE xPosition = :x AND yPosition = :y")
    suspend fun getTreesAtPosition(x: Int, y: Int): List<ForestTree>

    @Insert
    suspend fun insertForestTree(forestTree: ForestTree)

    @Update
    suspend fun UpdateForestTree(forestTree: ForestTree)

    @Delete
    suspend fun DeleteForestTree(forestTree: ForestTree)
}