package com.example.concentree.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val username: String,
    val coins: Int,
    val colorGreen: Boolean,
    val colorYellow: Boolean,
    val colorPink: Boolean,
    val colorRed: Boolean,
    val colorPurple: Boolean,
    val colorWhite: Boolean
)