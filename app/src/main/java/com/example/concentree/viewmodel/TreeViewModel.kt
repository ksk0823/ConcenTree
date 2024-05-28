package com.example.concentree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.concentree.roomDB.Tree
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TreeViewModelFactory(private val treeRepository: TreeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TreeViewModel::class.java)) {
            return TreeViewModel(treeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class TreeViewModel(private val treeRepository: TreeRepository) : ViewModel() {

    private val _treeList = MutableStateFlow<List<Tree>>(emptyList())
    val treeList = _treeList.asStateFlow()

    fun insertTree(tree: Tree) {
        viewModelScope.launch {
            treeRepository.insertTree(tree)
        }
    }

    fun getAllTrees() {
        viewModelScope.launch {
            treeRepository.getAllTrees().collect{
                _treeList.value = it
            }
        }
    }

    fun getTreesToGrow() {
        viewModelScope.launch {
            treeRepository.getTreesToGrow().collect{
                _treeList.value = it
            }
        }
    }

    fun getTreesInShop() {
        viewModelScope.launch {
            treeRepository.getTreesInShop().collect{
                _treeList.value = it
            }
        }
    }
    fun UpdateTree(tree: Tree){
        viewModelScope.launch {
            treeRepository.UpdateTree(tree)
        }
    }
    fun DeleteTree(tree: Tree){
        viewModelScope.launch {
            treeRepository.DeleteTree(tree)
        }
    }
}
