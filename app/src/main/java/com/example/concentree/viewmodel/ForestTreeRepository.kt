package com.example.concentree.viewmodel

import com.example.concentree.roomDB.ForestTree
import com.example.concentree.roomDB.ForestTreeDao

class ForestTreeRepository(private val forestTreeDao: ForestTreeDao) {

    suspend fun insertForestTree(forestTree: ForestTree) {
        forestTreeDao.insertForestTree(forestTree)
    }

    suspend fun getTreesInRange(startTime: Long, endTime: Long): List<ForestTree> {
        return forestTreeDao.getTreesInRange(startTime, endTime)
    }

    suspend fun getTreesAtPosition(x: Int, y: Int): List<ForestTree> {
        return forestTreeDao.getTreesAtPosition(x, y)
    }

    suspend fun UpdateForestTree(forestTree: ForestTree){
        forestTreeDao.UpdateForestTree(forestTree)
    }
    suspend fun DeleteForestTree(forestTree: ForestTree){
        forestTreeDao.DeleteForestTree(forestTree)
    }
}
