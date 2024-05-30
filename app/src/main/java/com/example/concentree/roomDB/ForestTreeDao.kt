package com.example.concentree.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDateTime

@Dao
interface ForestTreeDao {

    // 특정 시간 내 심었던 나무들만 가져오기
    // 통계 그래프용
    @Query("SELECT * FROM forest_tree WHERE startTime >= :startTime AND endTime <= :endTime")
    suspend fun getTreesInRange(startTime: LocalDateTime, endTime: LocalDateTime): List<ForestTree>

    // 정원에 나무 출력 용도로 위치 별로 나무 가져오기
    @Query("SELECT * FROM forest_tree WHERE xPosition = :x AND yPosition = :y")
    suspend fun getTreesAtPosition(x: Int, y: Int): List<ForestTree>

    // 정원에 현재 심어진 나무들만 불러오기
    @Query("SELECT * FROM forest_tree WHERE onForest = true")
    suspend fun getAllTreesInForest(): List<ForestTree>

    @Insert
    suspend fun insertForestTree(forestTree: ForestTree)

    @Update
    suspend fun UpdateForestTree(forestTree: ForestTree)

    @Delete
    suspend fun DeleteForestTree(forestTree: ForestTree)
}