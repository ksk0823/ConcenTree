package com.example.concentree.viewmodel

import com.example.concentree.roomDB.AppDatabase
import com.example.concentree.roomDB.Tree

class TreeRepository(private val db: AppDatabase) {

    val treeDao = db.getTreeDao()

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

    fun getTreesToGrow() = treeDao.getTreesToGrow()

    fun getTreesInShop() = treeDao.getTreesInShop()
}
