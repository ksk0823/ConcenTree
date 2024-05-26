package com.example.concentree.viewmodel

import com.example.concentree.roomDB.Tree
import com.example.concentree.roomDB.TreeDao

class TreeRepository(private val treeDao: TreeDao) {

    suspend fun insertTree(tree: Tree) {
        treeDao.insertTree(tree)
    }

    suspend fun UpdateTree(tree: Tree){
        treeDao.UpdateTree(tree)
    }
    suspend fun DeleteTree(tree: Tree){
        treeDao.DeleteTree(tree)
    }

    fun getAllTrees() = treeDao.getAllTrees()
}
