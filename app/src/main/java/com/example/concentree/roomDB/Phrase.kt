package com.example.concentree.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phrase")
data class Phrase(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val phrase: Phrase
    )
