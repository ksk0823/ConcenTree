package com.example.concentree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.concentree.roomDB.ForestTree
import com.example.concentree.roomDB.Phrase
import com.example.concentree.roomDB.Tree
import com.example.concentree.roomDB.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ViewModelFactory(private val phraseRepository: PhraseRepository,
                       private val forestTreeRepository: ForestTreeRepository,
                       private val treeRepository: TreeRepository,
                       private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModel::class.java)) {
            return ViewModel(phraseRepository, forestTreeRepository, treeRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ViewModel(private val phraseRepository: PhraseRepository,
                private val forestTreeRepository: ForestTreeRepository,
                private val treeRepository: TreeRepository,
                private val userRepository: UserRepository) : ViewModel() {

    private val _phraseList = MutableStateFlow<List<Phrase>>(emptyList())
    val phraseList = _phraseList.asStateFlow()

    private val _forestTreeList = MutableStateFlow<List<ForestTree>>(emptyList())
    val forestTreeList = _forestTreeList.asStateFlow()

    private val _treeList = MutableStateFlow<List<Tree>>(emptyList())
    val treeList = _treeList.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    // Phrase

    fun insertPhrase(phrase: Phrase) {
        viewModelScope.launch {
            phraseRepository.insertPhrase(phrase)
        }
    }

    fun getAllPhrases(){
        viewModelScope.launch {
            _phraseList.value = phraseRepository.getAllPhrases()
        }
    }

    fun UpdateForestTree(phrase: Phrase){
        viewModelScope.launch {
            phraseRepository.UpdatePhrase(phrase)
        }
    }
    fun DeleteForestTree(phrase: Phrase){
        viewModelScope.launch {
            phraseRepository.DeletePhrase(phrase)
        }
    }

    // ForestTree
    fun insertForestTree(forestTree: ForestTree) {
        viewModelScope.launch {
            forestTreeRepository.insertForestTree(forestTree)
        }
    }

    fun getTreesInRange(startTime: LocalDateTime, endTime: LocalDateTime) {
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

    // Tree

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

    // User

    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    fun getUserById(id: Int) {
        viewModelScope.launch {
            _user.value = userRepository.getUserById(id)
        }
    }

    fun updateUserCoins(id: Int, coins: Int) {
        viewModelScope.launch {
            userRepository.updateUserCoins(id, coins)
        }
    }
}