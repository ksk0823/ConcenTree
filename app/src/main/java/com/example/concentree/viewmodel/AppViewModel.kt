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

class AppViewModelFactory(private val phraseRepository: PhraseRepository,
                       private val forestTreeRepository: ForestTreeRepository,
                       private val treeRepository: TreeRepository,
                       private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            return AppViewModel(phraseRepository, forestTreeRepository, treeRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class AppViewModel(private val phraseRepository: PhraseRepository,
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

    init {
        viewModelScope.launch {
            val user = userRepository.getUserById(1)
            if (user == null) {
                // 최초 실행 시 사용자 생성 및 초기화
                val newUser = User(1, "user", 0
                    , false, false, false, false, false, false) // 초기 username은 "user"로 설정
                userRepository.InsertUser(newUser)
            }
            getUserById(1)
        }
    }

    // Phrase

    fun InsertPhrase(phrase: Phrase) {
        viewModelScope.launch {
            phraseRepository.InsertPhrase(phrase)
        }
    }

    fun UpdatePhrase(phrase: Phrase) {
        viewModelScope.launch {
            phraseRepository.UpdatePhrase(phrase)
        }
    }

    fun DeletePhrase(phrase: Phrase) {
        viewModelScope.launch {
            phraseRepository.DeletePhrase(phrase)
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

    fun InsertTree(tree: Tree) {
        viewModelScope.launch {
            treeRepository.InsertTree(tree)
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

    fun InsertUser(user: User) {
        viewModelScope.launch {
            userRepository.InsertUser(user)
        }
    }

    fun UpdateUser(user: User) {
        viewModelScope.launch {
            userRepository.UpdateUser(user)
        }
    }

    fun DeleteUser(user: User) {
        viewModelScope.launch {
            userRepository.DeleteUser(user)
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