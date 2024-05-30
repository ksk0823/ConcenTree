package com.example.concentree.viewmodel

import com.example.concentree.roomDB.Phrase
import com.example.concentree.roomDB.PhraseDao

class PhraseRepository(private val phraseDao: PhraseDao) {

    suspend fun getAllPhrases(): List<Phrase> {
        return phraseDao.getAllPhrases()
    }
    suspend fun insertPhrase(phrase: Phrase) {
        phraseDao.insertPhrase(phrase)
    }
    suspend fun UpdatePhrase(phrase: Phrase){
        phraseDao.UpdatePhrase(phrase)
    }
    suspend fun DeletePhrase(phrase: Phrase){
        phraseDao.DeletePhrase(phrase)
    }
}