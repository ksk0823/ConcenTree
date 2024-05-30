package com.example.concentree.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.concentree.roomDB.Phrase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PhraseViewModelFactory(private val phraseRepository: PhraseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhraseViewModel::class.java)) {
            return PhraseViewModel(phraseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class PhraseViewModel(private val phraseRepository: PhraseRepository) : ViewModel() {

    private val _phraseList = MutableStateFlow<List<Phrase>>(emptyList())
    val phraseList = _phraseList.asStateFlow()

    fun insertPhrase(phrase: Phrase) {
        viewModelScope.launch {
            phraseRepository.insertPhrase(phrase)
        }
    }

    fun getAllTreesInForest(){
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
}