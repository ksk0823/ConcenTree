package com.example.concentree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.concentree.roomDB.ForestTree
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForestTreeViewModelFactory(private val forestTreeRepository: ForestTreeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ForestTreeViewModel::class.java)) {
            return ForestTreeViewModel(forestTreeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ForestTreeViewModel(private val forestTreeRepository: ForestTreeRepository) : ViewModel() {

    private val _forestTreeList = MutableStateFlow<List<ForestTree>>(emptyList())
    val forestTreeList = _forestTreeList.asStateFlow()

    fun insertForestTree(forestTree: ForestTree) {
        viewModelScope.launch {
            forestTreeRepository.insertForestTree(forestTree)
        }
    }

    fun getTreesInRange(startTime: Long, endTime: Long) {
        viewModelScope.launch {
            _forestTreeList.value = forestTreeRepository.getTreesInRange(startTime, endTime)
        }
    }

    fun getTreesAtPosition(x: Int, y: Int) {
        viewModelScope.launch {
            _forestTreeList.value = forestTreeRepository.getTreesAtPosition(x, y)
        }
    }

    fun getAllTreesInForest(){
        viewModelScope.launch {
            _forestTreeList.value = forestTreeRepository.getAllTreesInForest()
        }
    }

    fun UpdateForestTree(forestTree: ForestTree){
        viewModelScope.launch {
            forestTreeRepository.UpdateForestTree(forestTree)
        }
    }
    fun DeleteForestTree(forestTree: ForestTree){
        viewModelScope.launch {
            forestTreeRepository.DeleteForestTree(forestTree)
        }
    }
}
