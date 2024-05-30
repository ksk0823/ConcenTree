package com.example.concentree.roomDB

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface PhraseDao {
    @Query("SELECT * FROM phrase")
    suspend fun getAllPhrases(): List<Phrase>

    @Insert
    suspend fun insertPhrase(phrase: Phrase)

    @Update
    suspend fun UpdatePhrase(phrase: Phrase)

    @Delete
    suspend fun DeletePhrase(phrase: Phrase)
}