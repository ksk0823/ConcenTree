package com.example.concentree.viewmodel

import com.example.concentree.roomDB.AppDatabase
import com.example.concentree.roomDB.ForestTree
import java.time.LocalDateTime

class ForestTreeRepository(private val db: AppDatabase) {

    val forestTreeDao = db.getForestTreeDao()

    suspend fun insertForestTree(forestTree: ForestTree) {
        forestTreeDao.insertForestTree(forestTree)
    }

    suspend fun getTreesInRange(startTime: LocalDateTime, endTime: LocalDateTime): List<ForestTree> {
        return forestTreeDao.getTreesInRange(startTime, endTime)
    }

    suspend fun getTreesAtPosition(x: Int, y: Int): List<ForestTree> {
        return forestTreeDao.getTreesAtPosition(x, y)
    }

    suspend fun getAllTreesInForest(): List<ForestTree> {
        return forestTreeDao.getAllTreesInForest()
    }

    suspend fun UpdateForestTree(forestTree: ForestTree){
        forestTreeDao.UpdateForestTree(forestTree)
    }
    suspend fun DeleteForestTree(forestTree: ForestTree){
        forestTreeDao.DeleteForestTree(forestTree)
    }
}
