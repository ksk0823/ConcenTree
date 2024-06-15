package com.example.concentree.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val username: String,
    val coins: Int,
    val colorGreen: Boolean =false,
    val colorYellow: Boolean=false,
    val colorPink: Boolean=false,
    val colorRed: Boolean=false,
    val colorPurple: Boolean=false,
    val colorWhite: Boolean=false
)