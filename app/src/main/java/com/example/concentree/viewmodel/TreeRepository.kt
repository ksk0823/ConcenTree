package com.example.concentree.viewmodel

import com.example.concentree.roomDB.AppDatabase
import com.example.concentree.roomDB.Tree
import com.example.concentree.roomDB.User

class TreeRepository(private val db: AppDatabase) {

    val treeDao = db.getTreeDao()

    suspend fun InsertTree(tree: Tree) {
        treeDao.InsertTree(tree)
    }

    suspend fun UpdateTree(tree: Tree){
        treeDao.UpdateTree(tree)
    }
    suspend fun DeleteTree(tree: Tree){
        treeDao.DeleteTree(tree)
    }

    suspend fun getTreeById(id: Int): Tree? {
        return treeDao.getTreeById(id)
    }

    fun getAllTrees() = treeDao.getAllTrees()

    fun getTreesToGrow() = treeDao.getTreesToGrow()

    fun getTreesInShop() = treeDao.getTreesInShop()
}
