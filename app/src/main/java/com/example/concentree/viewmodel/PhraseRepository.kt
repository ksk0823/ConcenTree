package com.example.concentree.viewmodel

import com.example.concentree.roomDB.AppDatabase
import com.example.concentree.roomDB.Phrase

class PhraseRepository(private val db: AppDatabase) {

    val phraseDao = db.getPhraseDao()

    suspend fun getAllPhrases(): List<Phrase> {
        return phraseDao.getAllPhrases()
    }
    suspend fun InsertPhrase(phrase: Phrase) {
        phraseDao.InsertPhrase(phrase)
    }
    suspend fun UpdatePhrase(phrase: Phrase){
        phraseDao.UpdatePhrase(phrase)
    }
    suspend fun DeletePhrase(phrase: Phrase){
        phraseDao.DeletePhrase(phrase)
    }
}